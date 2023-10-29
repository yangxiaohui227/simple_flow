package com.simple.flow;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ParseFlowXmlService implements InitializingBean

{

    @Autowired
    private Map<String, Invoker> invokerMap;
    public static final String configLocation="simple_flow.xml";
    public static final String NODE_INVOKER_NAME="invoker";
    public static final String NODE_BATCH_NAME="batch";


    public void parseFlowConfig(){
        PathMatchingResourcePatternResolver matchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = matchingResourcePatternResolver.getResources(configLocation);
            //ClassPathResource classPathResource = new ClassPathResource(configLocation);
            if(null==resources || resources.length==0){
                //配置文件不存在
                return;
            }
            // parse the XML as a W3C Document
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(resources[0].getInputStream());

            //Get an XPath object and evaluate the expression
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "/flow/chain";
            NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node chainNode = nodes.item(i);
                //执行器的名称
                if(!chainNode.hasAttributes()){
                    continue;
                }
                String nodeName = chainNode.getAttributes().item(0).getNodeValue();
                InvokerHolder invokerHolder = this.parseChainNode(chainNode);
                if(null!=invokerHolder){
                    //注册到调用链工具中
                    FlowInvokerChainUtil.addInvokerHolder(nodeName,invokerHolder);
                }
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }



    }

    private InvokerHolder parseChainNode(Node chainNode){
        InvokerHolder root=null;
        InvokerHolder parentInvoker=null;
        if(chainNode.hasChildNodes()){
            NodeList childNodes = chainNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if(StringUtils.isEmpty(item.getNodeName())){
                    continue;
                }
                //解析单个的节点
               if(NODE_INVOKER_NAME.equals(item.getNodeName())){
                   //解析Invoker Node
                   Invoker invokerNode = this.parseInvokerNode(item);
                   if(null!=invokerNode){
                       parentInvoker= buildInvokerChain(parentInvoker, Collections.singletonList(invokerNode));
                       if(null==root){
                           root=new SpendTimeLogInvokerHolder();
                           root.setNext(parentInvoker);
                       }
                   }
               }
               //解下并行节点
               if(NODE_BATCH_NAME.equals(item.getNodeName())){
                   //解下bachNode
                   List<Invoker> invokerList = parseBatchNode(item);
                   parentInvoker= buildInvokerChain(parentInvoker,invokerList);
                   if(null==root){
                       root=new SpendTimeLogInvokerHolder();
                       root.setNext(parentInvoker);
                   }
               }
            }
        }
        return root;
    }

    private InvokerHolder buildInvokerChain(InvokerHolder parent, List<Invoker> list){
        InvokerHolder invokerHolder = new InvokerHolder();
        invokerHolder.setInvokerList(list);
        if(null!=parent){
            parent.setNext(invokerHolder);
        }

        return invokerHolder;

    }

    private List<Invoker> parseBatchNode(Node batchNode){
        List<Invoker> invokerList=new ArrayList<>();
        if(batchNode.hasChildNodes()){
            NodeList childNodes = batchNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if(StringUtils.isEmpty(item.getNodeName())){
                    continue;
                }
                if(NODE_INVOKER_NAME.equals(item.getNodeName())){
                    //解析Invoker Node
                    Invoker invokerNode = this.parseInvokerNode(item);
                    invokerList.add(invokerNode);

                }
            }
        }
        return invokerList;
    }

    private Invoker parseInvokerNode(Node invokerNode){
        String nodeValue = invokerNode.getFirstChild().getNodeValue();
        if(!StringUtils.isEmpty(nodeValue)){
            Invoker invoker = invokerMap.get(nodeValue.trim());
            if(null==invoker){
                //打日志

                throw new RuntimeException("bean is not exist,may be you spend wrong,please check  beanName:"+nodeValue);
            }
            return invoker;
        }
        return null;

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //parse simple_flow.xml
       this.parseFlowConfig();
    }
}
