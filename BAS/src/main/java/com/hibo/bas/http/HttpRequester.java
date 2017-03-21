package com.hibo.bas.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

import com.hibo.bas.util.StrUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月1日 下午2:28:16</p>
 * <p>类全名：com.hibo.bas.http.HttpRequester</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class HttpRequester {
    private String defaultContentEncoding;

    public HttpRequester() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @param propertys
     *            请求属性

     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params,
            Map<String, String> propertys) throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @param propertys
     *            请求属性

     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params,
            Map<String, String> propertys) throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    /**
     * 发送HTTP请求
     *
     * @param urlString
     * @return 响映对象
     * @throws IOException
     */
    private HttpRespons send(String urlString, String method,
            Map<String, String> parameters, Map<String, String> propertys)
            throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        //urlConnection.sete

        if (propertys != null)
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }
    
    private String encoding = "utf-8";

    public void setEncoding(String encoding){
    	if(encoding==null || "".equals(encoding))
    		return;
    	this.encoding = encoding;
    }
    /**
     * 得到响应对象
     *
     * @param urlConnection
     * @return 响应对象
     * @throws IOException
     */
    private HttpRespons makeContent(String urlString,
            HttpURLConnection urlConnection) throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            InputStream in = urlConnection.getInputStream();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
            byte[] buff = new byte[1024];  
            int rc = 0;  
            while ((rc = in.read(buff, 0, 1024)) > 0) {  
                swapStream.write(buff, 0, rc);  
            }  
            byte[] in2b = swapStream.toByteArray();
            httpResponser.bytes = in2b;
//            StringBuffer buffer = new StringBuffer();
//            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in,encoding));
//            String line = "";
//            while ((line = bufferReader.readLine()) != null) {
//                buffer.append(line+ "\n");
//            }
//            String webpage= buffer.toString();
            //System.out.println(webpage);
//            httpResponser.webPagesText = webpage;
            httpResponser.webPagesText = new String(in2b, encoding);
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null)
                ecod = this.defaultContentEncoding;

            httpResponser.urlString = urlString;

            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();

            //httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();

            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
    public class HttpRespons {

        String urlString;

        int defaultPort;

        String file;

        String host;

        String path;

        int port;

        String protocol;

        String query;

        String ref;

        String userInfo;

        String contentEncoding;

        String content;

        String contentType;

        int code;

        String message;

        String method;

        int connectTimeout;

        int readTimeout;
        String webPagesText;
        byte[] bytes;
        
        //Vector<String> contentCollection;
        

        public String getContent() {
            return content;
        }

        public byte[] getBytes() {
			return bytes;
		}

		public String getContentType() {
            return contentType;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public Vector<String> getContentCollection() {
            //return contentCollection;
            return null;
        }

        public String getContentEncoding() {
            return contentEncoding;
        }

        public String getMethod() {
            return method;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public String getUrlString() {
            return urlString;
        }

        public int getDefaultPort() {
            return defaultPort;
        }

        public String getFile() {
            return file;
        }

        public String getHost() {
            return host;
        }

        public String getPath() {
            return path;
        }

        public int getPort() {
            return port;
        }

        public String getProtocol() {
            return protocol;
        }

        public String getQuery() {
            return query;
        }

        public String getRef() {
            return ref;
        }

        public String getUserInfo() {
            return userInfo;
        }
        
        public String getWebPagesText(){
        	return webPagesText;
        }
   }
   static public Object[][] getURLTableData(String url,String tableBeginStr,String tableEndStr,
           String rowBeginStr,String rowEndStr,String columnBeginStr,String columnEndStr,int columnCount) throws IOException
   {
           return getURLTableData(url,tableBeginStr,tableEndStr,rowBeginStr,rowEndStr,columnBeginStr,columnEndStr,columnCount,"utf-8");
   }
   
   static public Object[][] getURLTableData(String url,String tableBeginStr,String tableEndStr,
           String rowBeginStr,String rowEndStr,String columnBeginStr,String columnEndStr,int columnCount,String encoding) throws IOException
   {
	   HttpRequester httpRequester = new HttpRequester();
	   httpRequester.setEncoding(encoding);
       HttpRespons httpRespons = httpRequester.sendGet(url);
//       String webText = httpRespons.webPagesText;
       String webText = httpRespons.webPagesText.toLowerCase();
       //System.err.println(webText);
//       Object o2[][] = getHtmlTableData(webText,tableBeginStr,tableEndStr,rowBeginStr,rowEndStr,columnBeginStr,columnEndStr,columnCount);
       Object o2[][] = getHtmlTableData(webText,tableBeginStr.toLowerCase(),tableEndStr.toLowerCase(),
    		   rowBeginStr.toLowerCase(),rowEndStr.toLowerCase(),columnBeginStr.toLowerCase(),
    		   columnEndStr.toLowerCase(),columnCount);
       //snsoft.busibas.BusiBasPubl.println(o2);
       return o2;
   }
   
   
   static public Object[][] getHtmlTableData(String webText,String tableBeginStr,String tableEndStr,
           String rowBeginStr,String rowEndStr,String columnBeginStr,String columnEndStr,int columnCount)
   {
       String sBs = StrUtil.subStrAfter(webText,tableBeginStr);
       String end = StrUtil.subStrBefore(sBs,tableEndStr);

       java.util.ArrayList a = new java.util.ArrayList();
       while(end.indexOf(rowBeginStr)>=0)
       {
         end = StrUtil.subStrAfter(end, rowBeginStr);
         String valueArray[] = new String[columnCount];
         for(int v=0;v<valueArray.length;v++)
         {
             end = StrUtil.subStrAfter(end, columnBeginStr);
             valueArray[v] = StrUtil.subStrBefore(end, columnEndStr);
             end = StrUtil.subStrAfter(end, columnEndStr);
         }
         end = StrUtil.subStrAfter(end, rowEndStr);
         a.add(valueArray);
       }
       Object o2[][] = new Object[a.size()][columnCount];
       for(int i=0;i<o2.length;i++)
       {
           o2[i] =  (String[])a.get(i);;
           //snsoft.busibas.BusiBasPubl.println(valueArray);
       }
       return o2;
   }
 }
