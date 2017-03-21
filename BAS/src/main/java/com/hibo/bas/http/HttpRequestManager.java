
package com.hibo.bas.http;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;

import com.hibo.bas.http.httpClient.HttpProtocolHandler;
import com.hibo.bas.http.httpClient.HttpRequest;
import com.hibo.bas.http.httpClient.HttpResponse;
import com.hibo.bas.http.httpClient.HttpResultType;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年9月9日 下午5:56:04</p>
 * <p>类全名：com.hibo.bas.http.HttpRequestManager</p>
 * 作者：周雷
 * 初审：
 * 复审：
 */
public class HttpRequestManager {
	
	/**
	 * @功能:构建请求参数
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:10:52
	 * @param sParaTemp
	 * @param requestBody
	 * @param strMethod
	 * @param url
	 * @return
	 */
	private static HttpRequest getHttpRequest(Map<String, Object> sParaTemp,Object requestBody,String strMethod, String url,HttpResultType httpResultType) {
		if(null==url){
			throw new RuntimeException("URL不能为空");
		}
		if(null==strMethod){
			strMethod = HttpRequest.METHOD_GET;
		}
		if(null==httpResultType){
			httpResultType = HttpResultType.STRING;
		}
		HttpRequest httpRequest = new HttpRequest(httpResultType);
		//设置字符集
		httpRequest.setCharset(HttpStaticParams.INPUT_CHARSET);
		//设置url
		httpRequest.setUrl(url);
		//设置请求方法
		httpRequest.setMethod(strMethod);
		//设置请求主体
		httpRequest.setRequestBody(requestBody);
		//设置参数
		if(null!=sParaTemp&&sParaTemp.size()>0){
			if(HttpRequest.METHOD_POST.equals(strMethod)){
				NameValuePair[] nameValuePair = new NameValuePair[sParaTemp.size()];
				int  i=0;
				for (String key : sParaTemp.keySet()) {
					nameValuePair[i++] = new NameValuePair(key, sParaTemp.get(key).toString());
				}
				httpRequest.setParameters(nameValuePair);
			}else if(HttpRequest.METHOD_GET.equals(strMethod)){
				String queryString = null;
				for (String key : sParaTemp.keySet()) {
					if(null==queryString){
						queryString = key+"="+sParaTemp.get(key);
					}else{
						queryString +="&"+key+"="+sParaTemp.get(key);
					}
				}
				httpRequest.setQueryString(queryString);
			}
		}
		return httpRequest;
	}
	/**
	 * @功能:发送请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:06:01
	 * @param sParaTemp 参数map map的值为String
	 * @param strMethod 请求方法
	 * @param url url地址
	 * @return 
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponse sendRequest(Map<String, Object> sParaTemp,String strMethod,String url,HttpResultType httpResultType) throws HttpException, IOException{
		HttpRequest httpRequest = getHttpRequest(sParaTemp,null,strMethod,url,httpResultType);
		HttpResponse httpResponse = null;
		httpResponse = HttpProtocolHandler.getInstance().execute(httpRequest, null, null);
		return httpResponse;
	}
	
	/**
	 * @功能:发送请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:06:54
	 * @param requestBody 请求类型可以是 String、InputStream 、 NameValuePair[]
	 * @param strMethod 方法
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpResponse sendRequest(Object requestBody,String strMethod,String url,HttpResultType httpResultType) throws HttpException, IOException{
		HttpRequest httpRequest = getHttpRequest(null,requestBody,strMethod,url,httpResultType);
		HttpResponse httpResponse = null;
		httpResponse = HttpProtocolHandler.getInstance().execute(httpRequest, null, null);
		return httpResponse;
	}
	
	/**
	 * @功能:发送请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String sendPostRequest(String url) throws UnsupportedEncodingException, HttpException, IOException{
		return sendPostRequest(null,url);
	}
	
	/**
	 * @功能:发送请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static byte[] sendPostRequest2(String url) throws UnsupportedEncodingException, HttpException, IOException{
		return sendPostRequest2(null,url);
	}
	/**
	 * @功能:发送post请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param sParaTemp 参数map map的值为String
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String sendPostRequest(Map<String, Object> sParaTemp,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(sParaTemp, HttpRequest.METHOD_POST,url,null);
		if(null==httpResponse){
			return null;
		}
		String httpResponseStr = httpResponse.getStringResult();
		return httpResponseStr;
	}
	
	/**
	 * @功能:发送post请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:10:03
	 * @param requestBody 请求类型可以是 String、InputStream 、 NameValuePair[]
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String sendPostRequest(Object requestBody,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(requestBody, HttpRequest.METHOD_POST,url,null);
		if(null==httpResponse){
			return null;
		}
		String httpResponseStr = httpResponse.getStringResult();
		return httpResponseStr;
	}
	
	/**
	 * @功能:发送get请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param sParaTemp 参数map map的值为String
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String sendGetRequest(Map<String, Object> sParaTemp,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(sParaTemp, HttpRequest.METHOD_GET,url,null);
		if(null==httpResponse){
			return null;
		}
		String httpResponseStr = httpResponse.getStringResult();
		return httpResponseStr;
	}
	
	/**
	 * @功能:发送get请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:10:03
	 * @param requestBody 请求类型可以是 String、InputStream 、 NameValuePair[]
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String sendGetRequest(Object requestBody,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(requestBody, HttpRequest.METHOD_GET,url,null);
		if(null==httpResponse){
			return null;
		}
		String httpResponseStr = httpResponse.getStringResult();
		return httpResponseStr;
	}
	
	
	/**
	 * @功能:发送post请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param sParaTemp 参数map map的值为String
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static byte[] sendPostRequest2(Map<String, Object> sParaTemp,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(sParaTemp, HttpRequest.METHOD_POST,url,HttpResultType.BYTES);
		if(null==httpResponse){
			return null;
		}
		return httpResponse.getByteResult();
	}
	
	/**
	 * @功能:发送post请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:10:03
	 * @param requestBody 请求类型可以是 String、InputStream 、 NameValuePair[]
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static byte[] sendPostRequest2(Object requestBody,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(requestBody, HttpRequest.METHOD_POST,url,HttpResultType.BYTES);
		if(null==httpResponse){
			return null;
		}
		return httpResponse.getByteResult();
	}
	
	/**
	 * @功能:发送get请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:08:40
	 * @param sParaTemp 参数map map的值为String
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static byte[] sendGetRequest2(Map<String, Object> sParaTemp,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(sParaTemp, HttpRequest.METHOD_GET,url,HttpResultType.BYTES);
		if(null==httpResponse){
			return null;
		}
		return httpResponse.getByteResult();
	}
	
	/**
	 * @功能:发送get请求
	 * @作者:周雷
	 * @时间:2015年11月27日 下午8:10:03
	 * @param requestBody 请求类型可以是 String、InputStream 、 NameValuePair[]
	 * @param url 请求地址
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws HttpException
	 * @throws IOException
	 */
	public static byte[] sendGetRequest2(Object requestBody,String url) throws UnsupportedEncodingException, HttpException, IOException{
		HttpResponse httpResponse = sendRequest(requestBody, HttpRequest.METHOD_GET,url,HttpResultType.BYTES);
		if(null==httpResponse){
			return null;
		}
		return httpResponse.getByteResult();
	}
	
}
