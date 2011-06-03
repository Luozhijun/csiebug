package csiebug.web.html.form.upload;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;

public class HtmlUploadify extends HtmlComponentNoBody {
	private String uploadId;
	private String version = "jquery.uploadify-v2.1.4";
	
	//options
	private String auto;
	private String buttonImg;
	private String buttonText;
	private String cancelImg;
	private String checkScript;
	private String displayData;
	private String expressInstall;
	private String fileExt;
	private String folder;
	private String height;
	private String hideButton;
	private String method;
	private String multi;
	private String queueID;
	private String queueSizeLimit;
	private String removeCompleted;
	private String rollover;
	private String script;
	private String scriptData;
	private String simUploadLimit;
	private String sizeLimit;
	private String width;
	
	//events
	private String onAllComplete;
	private String onCancel;
	private String onCheck;
	private String onClearQueue;
	private String onComplete;
	private String onError;
	private String onInit;
	private String onOpen;
	private String onProgress;
	private String onQueueFull;
	private String onSelect;
	private String onSelectOnce;
	private String onSWFReady;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlUploadify(String version, String id, String auto, String buttonImg, String buttonText, String cancelImg, String checkScript, String displayData, String expressInstall, String fileExt, String folder, String height, String hideButton, String method, String multi, String queueID, String queueSizeLimit, String removeCompleted, String rollover, String script, String scriptData, String simUploadLimit, String sizeLimit, String width, String onAllComplete, String onCancel, String onCheck, String onClearQueue, String onComplete, String onError, String onInit, String onOpen, String onProgress, String onQueueFull, String onSelect, String onSelectOnce, String onSWFReady) {
		if(AssertUtility.isNotNullAndNotSpace(version)) {
			this.version = version;
		}
		
		this.uploadId = id;
		this.auto = auto;
		this.buttonImg = buttonImg;
		this.buttonText = buttonText;
		this.cancelImg = cancelImg;
		this.checkScript = checkScript;
		this.displayData = displayData;
		this.expressInstall = expressInstall;
		this.fileExt = fileExt;
		this.folder = folder;
		this.height = height;
		this.hideButton = hideButton;
		this.method = method;
		this.multi = multi;
		this.queueID = queueID;
		this.queueSizeLimit = queueSizeLimit;
		this.removeCompleted = removeCompleted;
		this.rollover = rollover;
		this.script = script;
		this.scriptData = scriptData;
		this.simUploadLimit = simUploadLimit;
		this.sizeLimit = sizeLimit;
		this.width = width;
		this.onAllComplete = onAllComplete;
		this.onCancel = onCancel;
		this.onCheck = onCheck;
		this.onClearQueue = onClearQueue;
		this.onComplete = onComplete;
		this.onError = onError;
		this.onInit = onInit;
		this.onOpen = onOpen;
		this.onProgress = onProgress;
		this.onQueueFull = onQueueFull;
		this.onSelect = onSelect;
		this.onSelectOnce = onSelectOnce;
		this.onSWFReady = onSWFReady;
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
			
			if(AssertUtility.isNotNullAndNotSpace(queueID)) {
				htmlBuilder.divStart().id(queueID).tagClose().divEnd();
			}
			htmlBuilder.inputStart().type("file").id(uploadId).name(uploadId).tagClose();
		}
		
		return htmlBuilder.toString();
	}

	@Override
	public String renderEnd() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		htmlBuilder.inputEnd();
		
		if(AssertUtility.isNotNullAndNotSpace(uploadId)) {
			String linkText = "Upload Files";
			try {
				linkText = webutil.getMessage("uploadify.uploadFiles");
			} catch (UnsupportedEncodingException e) {
				String message = "Unsupport encoding Excpetion!!\nCheck your message resource file format!!";
		    	webutil.getLogger().debug(message);
			} catch (NamingException e) {
				String message = "Name 'uploadify.uploadFiles' is not bound in this Context!";
		    	webutil.getLogger().debug(message);
			}
			
			if(!AssertUtility.isTrue(auto)) {
				htmlBuilder.aStart().href("javascript:$('#" + uploadId + "').uploadifyUpload();").tagClose().text(linkText).aEnd();
			}
		}
		
		return htmlBuilder.toString();
	}
	
	private void makeOnLoadScript() throws NamingException, UnsupportedEncodingException {
		StringBuffer script = new StringBuffer();
		
		script.append("$('#" + uploadId + "').uploadify({\n");
		
		if(AssertUtility.isTrue(auto)) {
			script.append("	'auto'      : true,\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(buttonImg)) {
			script.append(" 'buttonImg'	: '" + buttonImg + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(buttonText)) {
			script.append("	'buttonText': '" + buttonText + "',\n");
		} else {
			script.append("	'buttonText': '" + webutil.getMessage("uploadify.selectFiles") + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(displayData) && displayData.equalsIgnoreCase("speed")) {
			script.append("	'displayData': 'speed',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(expressInstall)) {
			script.append("	'expressInstall':'" + expressInstall + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(fileExt)) {
			script.append("	'fileExt':'" + fileExt + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(cancelImg)) {
			script.append("	'cancelImg' : '" + webutil.getBasePathForHTML() + cancelImg + "',\n");
		} else {
			script.append("	'cancelImg' : '" + webutil.getBasePathForHTML() + "js/" + version + "/cancel.png',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(folder)) {
//			script.append("	'folder'    : '" + folder + "',\n");
			
			if(AssertUtility.isNotNullAndNotSpace(checkScript)) {
				script.append("	'checkScript'    : '" + checkScript + "?uploadFolder=" + folder + "',\n");
			} else {
				script.append("	'checkScript'    : '" + webutil.getBasePathForHTML() + "uploadify/uploadifyCheckJSONData.jsp?uploadFolder=" + folder + "',\n");
			}
			
			if(AssertUtility.isNotNullAndNotSpace(this.script)) {
				script.append("	'script': '" + this.script + "?uploadFolder=" + folder + "',\n");
			} else {
				script.append("	'script': '" + webutil.getBasePathForHTML() + "uploadify/uploadify.jsp?uploadFolder=" + folder + "',\n");
			}
		} else {
			if(AssertUtility.isNotNullAndNotSpace(checkScript)) {
				script.append("	'checkScript'    : '" + checkScript + "',\n");
			} else {
				script.append("	'checkScript'    : '" + webutil.getBasePathForHTML() + "uploadify/uploadifyCheckJSONData.jsp',\n");
			}
			
			if(AssertUtility.isNotNullAndNotSpace(this.script)) {
				script.append("	'script': '" + this.script + "',\n");
			} else {
				script.append("	'script': '" + webutil.getBasePathForHTML() + "uploadify/uploadify.jsp',\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(height)) {
			script.append("	'height'    : '" + height + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(hideButton)) {
			if(AssertUtility.isTrue(hideButton)) {
				script.append("	'hideButton'    : true,\n");
			} else {
				script.append("	'hideButton'    : false,\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(method)) {
			if(method.trim().equalsIgnoreCase("get")) {
				script.append("	'method'    : 'GET',\n");
			} else {
				script.append("	'method'    : 'POST',\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(multi)) {
			if(AssertUtility.isTrue(multi)) {
				script.append("	'multi'    : true,\n");
			} else {
				script.append("	'multi'    : false,\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(queueID)) {
			script.append("	'queueID'    : '" + queueID + "',\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(queueSizeLimit)) {
			script.append("	'queueSizeLimit'    : " + queueSizeLimit + ",\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(removeCompleted)) {
			if(AssertUtility.isFalse(removeCompleted)) {
				script.append("	'removeCompleted'    : false,\n");
			} else {
				script.append("	'removeCompleted'    : true,\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(rollover)) {
			if(AssertUtility.isTrue(rollover)) {
				script.append("	'rollover'    : true,\n");
			} else {
				script.append("	'rollover'    : false,\n");
			}
		}
		
		script.append("	'scriptAccess'    : 'always',\n");
		
		if(AssertUtility.isNotNullAndNotSpace(scriptData)) {
			script.append("	'scriptData'    : " + scriptData + ",\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(simUploadLimit)) {
			script.append("	'simUploadLimit'    : " + simUploadLimit + ",\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(sizeLimit)) {
			script.append("	'sizeLimit'    : " + sizeLimit + ",\n");
		}
		
		if(AssertUtility.isNotNullAndNotSpace(width)) {
			script.append("	'width'    : " + width + ",\n");
		} else {
			//如果是用預設的中文文字,寬度給109是最剛好,不會露出flash的白色範圍
			//如果改了MessageResource檔的對應文字發現寬度不符合,還是可以利用設定width的方式調整
			//不會因為這邊的預設值影響
			if(!AssertUtility.isNotNullAndNotSpace(buttonText)) {
				script.append("	'width'    : 109,\n");
			}
		}
		
		if(AssertUtility.isNotNullAndNotSpace(onAllComplete)) {
			script.append("	'onAllComplete'    : " + onAllComplete + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onCancel)) {
			script.append("	'onCancel'    : " + onCancel + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onCheck)) {
			script.append("	'onCheck'    : " + onCheck + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onClearQueue)) {
			script.append("	'onClearQueue'    : " + onClearQueue + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onComplete)) {
			script.append("	'onComplete'    : " + onComplete + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onError)) {
			script.append("	'onError'    : " + onError + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onInit)) {
			script.append("	'onInit'    : " + onInit + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onOpen)) {
			script.append("	'onOpen'    : " + onOpen + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onProgress)) {
			script.append("	'onProgress'    : " + onProgress + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onQueueFull)) {
			script.append("	'onQueueFull'    : " + onQueueFull + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onSelect)) {
			script.append("	'onSelect'    : " + onSelect + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onSelectOnce)) {
			script.append("	'onSelectOnce'    : " + onSelectOnce + ",\n");
		}
		if(AssertUtility.isNotNullAndNotSpace(onSWFReady)) {
			script.append("	'onSWFReady'    : " + onSWFReady + ",\n");
		}
		
		script.append("	'uploader'  : '" + webutil.getBasePathForHTML() + "js/" + version + "/uploadify.swf'\n");
		
		script.append("});\n");

		webutil.addPageLoadScript(script.toString());
	}
}
