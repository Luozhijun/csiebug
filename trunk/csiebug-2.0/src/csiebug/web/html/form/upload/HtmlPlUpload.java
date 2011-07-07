package csiebug.web.html.form.upload;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;

public class HtmlPlUpload extends HtmlComponentNoBody {
	private String uploadId;
	
	//General options
	private String runtimes;
	private String url;
	private String maxFileSize;
	private String chunkSize;
	private String uniqueNames;
	private String resizeWidth;
	private String resizeHeight;
	private String resizeQuality;
	private List<Map<String, String>> filters;
	private String flashSWFURL;
	private String silverlightXAPURL;
	private String browseButton;
	private String dropElement;
	private String container;
	private String multipart;
	private String multipartParams;
	private String requiredFeatures;
	private String folder;
	//TODO: 尚未確認值
	@SuppressWarnings("unused")
	private String headers;
	
	//Queue widget specific options
	private Map<String, String> preinit;
	private String dragdrop;
	private String rename;
	private String multipleQueues;
	private String urlstreamUpload;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlPlUpload(String id, String runtimes, String url, String maxFileSize, String chunkSize, String uniqueNames, String resizeWidth, String resizeHeight, String resizeQuality, List<Map<String, String>> filters, String flashSWFURL, String silverlightXAPURL, String browseButton, String dropElement, String container, String multipart, String multipartParams, String requiredFeatures, String headers, Map<String, String> preinit, String dragdrop, String rename, String multipleQueues, String urlstreamUpload, String folder) {
		this.uploadId = id;
		
		//General options
		this.runtimes = runtimes;
		this.url = url;
		this.maxFileSize = maxFileSize;
		this.chunkSize = chunkSize;
		this.uniqueNames = uniqueNames;
		this.resizeWidth = resizeWidth;
		this.resizeHeight = resizeHeight;
		this.resizeQuality = resizeQuality;
		this.filters = filters;
		this.flashSWFURL = flashSWFURL;
		this.silverlightXAPURL = silverlightXAPURL;
		this.browseButton = browseButton;
		this.dropElement = dropElement;
		this.container = container;
		this.multipart = multipart;
		this.multipartParams = multipartParams;
		this.requiredFeatures = requiredFeatures;
		this.headers = headers;
		this.folder = folder;
		
		//Queue widget specific options
		this.preinit = preinit;
		this.dragdrop = dragdrop;
		this.rename = rename;
		this.multipleQueues = multipleQueues;
		this.urlstreamUpload = urlstreamUpload;	
	}
	
	@Override
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(uploadId)) {
			try {
				makeOnLoadScript();
			} catch (NamingException e) {
				throw new HtmlRenderException(e);
			} catch (UnsupportedEncodingException e) {
				throw new HtmlRenderException(e);
			}
			
			htmlBuilder.divStart().id(uploadId).tagClose().p();
			if(!AssertUtility.isNotNullAndNotSpace(runtimes)) {
				htmlBuilder.text("You browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.");
			} else {
				String[] runtimeArray = runtimes.split(",");
				StringBuffer message = new StringBuffer();
				for(int i = 0; i < runtimeArray.length; i++) {
					if(i != 0) {
						if(i == runtimeArray.length - 1) {
							message.append(" or ");
						} else {
							message.append(", ");
						}
					}
					
					message.append(runtimeArray[i]);
				}
				
				htmlBuilder.text("You browser doesn't have " + message.toString() + " support.");
			}
			htmlBuilder.pEnd().divEnd();
		}
		
		return htmlBuilder.toString();
	}

	@Override
	public String renderEnd() throws HtmlRenderException {
		return "";
	}
	
	@SuppressWarnings("unchecked")
	private void makeOnLoadScript() throws NamingException, UnsupportedEncodingException {
		StringBuffer script = new StringBuffer();
		
		script.append("$('#" + uploadId + "').pluploadQueue({\n");
		boolean hasOneAttribute = false;
		if(AssertUtility.isNotNullAndNotSpace(runtimes)) {
			script.append("	runtimes : '" + runtimes + "'");
			hasOneAttribute = true;
		} else {
			script.append("	runtimes : 'html4'");
			hasOneAttribute = true;
		}
		
		if(AssertUtility.isNotNullAndNotSpace(url)) {
			setStringAttribute(script, "url", url, hasOneAttribute);
		} else {
			setStringAttribute(script, "url", webutil.getBasePathForHTML() + "plupload/plupload.jsp", hasOneAttribute);
		}
		
		setStringAttribute(script, "max_file_size", maxFileSize, hasOneAttribute);
		setStringAttribute(script, "chunk_size", chunkSize, hasOneAttribute);
		setBooleanAttributeDefaultFalse(script, "unique_names", uniqueNames, hasOneAttribute);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("width", resizeWidth);
		map.put("height", resizeHeight);
		map.put("quality", resizeQuality);
		setMapAttribute(script, "resize", map, "integer", hasOneAttribute);
		setListMapAttribute(script, "filters", filters, hasOneAttribute);
		//如果runtimes裏面有指定flash,如果沒有設定flash的路徑,則帶預設值
		if(AssertUtility.isNotNullAndNotSpace(runtimes) && runtimes.toLowerCase().indexOf("flash") != -1 && !AssertUtility.isNotNullAndNotSpace(flashSWFURL)) {
			setStringAttribute(script, "flash_swf_url", webutil.getBasePathForHTML() + "js/plupload/js/plupload.flash.swf", hasOneAttribute);
		} else {
			setStringAttribute(script, "flash_swf_url", flashSWFURL, hasOneAttribute);
		}
		//如果runtimes裏面有指定silverlight,如果沒有設定silverlight的路徑,則帶預設值
		if(AssertUtility.isNotNullAndNotSpace(runtimes) && runtimes.toLowerCase().indexOf("silverlight") != -1 && !AssertUtility.isNotNullAndNotSpace(silverlightXAPURL)) {
			setStringAttribute(script, "silverlight_xap_url", webutil.getBasePathForHTML() + "js/plupload/js/plupload.silverlight.xap", hasOneAttribute);
		} else {
			setStringAttribute(script, "silverlight_xap_url", silverlightXAPURL, hasOneAttribute);
		}
		//如果runtimes裏面有指定flash,html5或silverlight,如果沒有設定browseButton,則帶入uploadId + "_browseButton"
		if(AssertUtility.isNotNullAndNotSpace(runtimes) && (runtimes.toLowerCase().indexOf("flash") != -1 || runtimes.toLowerCase().indexOf("html5") != -1 || runtimes.toLowerCase().indexOf("silverlight") != -1) && !AssertUtility.isNotNullAndNotSpace(browseButton)) {
			setStringAttribute(script, "browse_button", uploadId + "_browseButton", hasOneAttribute);
		} else {
			setStringAttribute(script, "browse_button", browseButton, hasOneAttribute);
		}
		setStringAttribute(script, "drop_element", dropElement, hasOneAttribute);
		setStringAttribute(script, "container", container, hasOneAttribute);
		setBooleanAttributeDefaultFalse(script, "multipart", multipart, hasOneAttribute);
		setBooleanAttributeDefaultTrue(script, "dragdrop", dragdrop, hasOneAttribute);
		setBooleanAttributeDefaultFalse(script, "multiple_queues", multipleQueues, hasOneAttribute);
		setMapAttribute4BindingEvent(script, "preinit", preinit, hasOneAttribute);
		setBooleanAttributeDefaultFalse(script, "rename", rename, hasOneAttribute);
		setStringAttribute(script, "required_features", requiredFeatures, hasOneAttribute);
		setBooleanAttributeDefaultFalse(script, "urlstream_upload", urlstreamUpload, hasOneAttribute);
		if(AssertUtility.isNotNullAndNotSpace(multipartParams) && webutil.getRequestAttribute(multipartParams) != null) {
			Map<String, String> params = (Map<String, String>)webutil.getRequestAttribute(multipartParams);
			if(!AssertUtility.isNotNullAndNotSpace(params.get("uploadFolder")) && AssertUtility.isNotNullAndNotSpace(folder)) {
				params.put("uploadFolder", folder);
			}
			setMapAttribute(script, "multipart_params", params, null, hasOneAttribute);
		} else if(AssertUtility.isNotNullAndNotSpace(folder)) {
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("uploadFolder", folder);
			setMapAttribute(script, "multipart_params", params, null, hasOneAttribute);
		}
		
		script.append("\n});\n");

		webutil.addPageLoadScript(script.toString());
	}
	
	private void setStringAttribute(StringBuffer script, String key, String value, boolean hasOneAttribute) {
		if(AssertUtility.isNotNullAndNotSpace(value)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : '" + value + "'");
			hasOneAttribute = true;
		}
	}
	
	private void setBooleanAttributeDefaultTrue(StringBuffer script, String key, String value, boolean hasOneAttribute) {
		if(AssertUtility.isNotNullAndNotSpace(value)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			
			if(AssertUtility.isFalse(value)) {
				script.append("	" + key + " : false");
			} else {
				script.append("	" + key + " : true");
			}
			hasOneAttribute = true;
		}
	}
	
	private void setBooleanAttributeDefaultFalse(StringBuffer script, String key, String value, boolean hasOneAttribute) {
		if(AssertUtility.isNotNullAndNotSpace(value)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			
			if(AssertUtility.isTrue(value)) {
				script.append("	" + key + " : true");
			} else {
				script.append("	" + key + " : false");
			}
			hasOneAttribute = true;
		}
	}
	
	private void setMapAttribute(StringBuffer script, String key, Map<String, String> values, String dataType, boolean hasOneAttribute) {
		if(!AssertUtility.isAllElementNull(values)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : " + getMapAttribute(values, dataType));
			hasOneAttribute = true;
		}
	}
	
	private String getMapAttribute(Map<String, String> values, String dataType) {
		if(!AssertUtility.isAllElementNull(values)) {
			StringBuffer value = new StringBuffer("{");
			Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if(entry.getValue() != null) {
					if(!value.toString().trim().equals("{")) {
						value.append(", ");
					}
					
					if(dataType != null && dataType.trim().equalsIgnoreCase("integer")) {
						value.append(entry.getKey() + " : " + entry.getValue());
					} else {
						value.append(entry.getKey() + " : '" + entry.getValue() + "'");
					}
				}
			}
			value.append("}");
			
			return value.toString();
		} else {
			return "";
		}
	}
	
	private void setMapAttribute4BindingEvent(StringBuffer script, String key, Map<String, String> values, boolean hasOneAttribute) {
		if(values != null && !AssertUtility.isAllElementNull(values)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : " + getMapAttribute4BindingEvent(values));
			hasOneAttribute = true;
		}
	}
	
	private String getMapAttribute4BindingEvent(Map<String, String> values) {
		if(!AssertUtility.isAllElementNull(values)) {
			StringBuffer value = new StringBuffer("{");
			Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if(entry.getValue() != null) {
					if(!value.toString().trim().equals("{")) {
						value.append(", ");
					}
					
					value.append(entry.getKey() + " : " + entry.getValue());
				}
			}
			value.append("}");
			
			return value.toString();
		} else {
			return "";
		}
	}
	
	private void setListMapAttribute(StringBuffer script, String key, List<Map<String, String>> values, boolean hasOneAttribute) {
		if(values != null && values.size() != 0) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : [\n");
			for(int i = 0; i < values.size(); i++) {
				if(i != 0) {
					script.append(",\n");
				}
				script.append("		" + getMapAttribute(values.get(i), null));
			}
			script.append("	]");
			hasOneAttribute = true;
		}
	}
}
