package csiebug.web.html.form;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.DateFormatException;
import csiebug.util.DateFormatUtility;
import csiebug.util.NumberFormatUtility;
import csiebug.util.StringUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponent;
import csiebug.web.html.HtmlRenderException;


/**
 * 產生HTML TextInterval
 * @author George_Tsai
 * @version 2009/6/15
 */

public class HtmlTextInterval extends HtmlComponent {
	private String htmlId;
	private String name;
	private String isReadOnly;
	private String className;
	private String isReturnValue;
	private String defaultValueStart;
	private String defaultValueEnd;
	private String dataType;
	private String isMasked;
	private String onChange;
	private String onBlur;
	private String onClick;
	private String onKeyDown;
	private String header;
	private String headerClass;
	private String footer;
	private String footerClass;
	private String buttonClass;
	private String isRequired;
	private String userValueStart;
	private String userValueEnd;
	private OpEnum op;
	private String maxlength;
	private String fixlength;
	private String size;
	private String style;
	private String maxvalue;
	private String minvalue;
	private String maxinteger;
	private String maxdecimal;
	private String numberStep;
	private String imagePath;
	private String typesetting;
	
	private WebUtility webutil = new WebUtility();
	
	public HtmlTextInterval(String htmlId, String name, String isReadOnly, String className, String isReturnValue, String defaultValueStart, String defaultValueEnd, String dataType, String isMasked, String onChange, String onBlur, String onClick, String onKeyDown, String header, String headerClass, String footer, String footerClass, String buttonClass, String isRequired, String userValueStart, String userValueEnd, OpEnum op, String maxlength, String fixlength, String size, String style, String maxvalue, String minvalue, String maxinteger, String maxdecimal, String numberStep, String imagePath, String typesetting) {
		this.htmlId = htmlId;
		this.name = name;
		this.isReadOnly = isReadOnly;
		this.className = className;
		this.isReturnValue = isReturnValue;
		this.defaultValueStart = defaultValueStart;
		this.defaultValueEnd = defaultValueEnd;
		this.dataType = dataType;
		this.isMasked = isMasked;
		this.onChange = onChange;
		this.onBlur = onBlur;
		this.onClick = onClick;
		this.onKeyDown = onKeyDown;
		this.header = header;
		this.headerClass = headerClass;
		this.footer = footer;
		this.footerClass = footerClass;
		this.buttonClass = buttonClass;
		this.isRequired = isRequired;
		this.userValueStart = userValueStart;
		this.userValueEnd = userValueEnd;
		this.op = op;
		this.maxlength = maxlength;
		this.fixlength = fixlength;
		this.size = size;
		this.style = style;
		this.maxvalue = maxvalue;
		this.minvalue = minvalue;
		this.maxinteger = maxinteger;
		this.maxdecimal = maxdecimal;
		this.numberStep = numberStep;
		this.imagePath = StringUtility.removeStartEndSlash(imagePath);
		this.typesetting = typesetting;
	}
	
	public HtmlTextInterval() {
		
	}
	
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
		try {
	        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
	            htmlBuilder.tdStart().className("TdHeader").tagClose();
	            
	            //建立header
            	String strIsRequired = "false";
        		if(htmlId != null) {
        	        if(webutil.getRequestAttribute(htmlId + "_IsRequired") != null) {
        	        	if(webutil.getRequestAttribute(htmlId + "_IsRequired").toString().equals("true")) {
        	        		strIsRequired = "true";
        	        	}
        	        } else {
        	        	if(AssertUtility.isTrue(isRequired)) {
        	            	strIsRequired = "true";
        	            }
        	        }
                } else {
                	if(AssertUtility.isTrue(isRequired)) {
                    	strIsRequired = "true";
                    }
                }
        		
        		if(header != null || strIsRequired.equalsIgnoreCase("true")) {
                    htmlBuilder.labelStart();
                    if(htmlId != null) {
                    	htmlBuilder.id(htmlId + "_header");
                    }
                    if(name != null) {
                    	htmlBuilder.name(name + "_header");
                    }
                    if(headerClass != null) {
                    	htmlBuilder.className(headerClass);
                    } else {
                    	htmlBuilder.className("LabelHeader");
                    }
                    htmlBuilder.tagClose();
                    if(strIsRequired.equalsIgnoreCase("true")) {
                    	htmlBuilder.appendString(webutil.getMessage("common.star"));
                    }
                    if(header != null) {
                    	htmlBuilder.text(header + "：");
                    }
                    htmlBuilder.labelEnd();
                    htmlBuilder.text("  ");
                }
			
				htmlBuilder.tdEnd();
	            
	            htmlBuilder.tdStart().className("TdBody").tagClose();
	        }
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		}
		
        return htmlBuilder.toString();
	}
	
	public String renderBody(String content) throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
        //建立主體
        try {
        	renderSelectIndividual(htmlBuilder, 1);
        	renderBodyIndividual(htmlBuilder, 1);
    		renderFooter(htmlBuilder);
    		htmlBuilder.text(" ~ ");
    		renderSelectIndividual(htmlBuilder, 2);
    		renderBodyIndividual(htmlBuilder, 2);
    		renderFooter(htmlBuilder);
		} catch (UnsupportedEncodingException e) {
			throw new HtmlRenderException(e);
		} catch (NamingException e) {
			throw new HtmlRenderException(e);
		} catch (DateFormatException e) {
			throw new HtmlRenderException(e);
		}
        
        return htmlBuilder.toString();
	}
	
	public String renderEnd() {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
        
        if(typesetting == null || !typesetting.equalsIgnoreCase("false")) {
        	htmlBuilder.tdEnd();
        }
        
        return htmlBuilder.toString();
	}
	
	/**
	 * @param HtmlBuilder
	 * @param type(1:開始,2:結束)
	 * @author George_Tsai
	 * @version 2011/2/18
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 * @throws HtmlRenderException 
	 * @throws DateFormatException 
	 */
	private void renderSelectIndividual(HtmlBuilder htmlBuilder, int type) throws UnsupportedEncodingException, NamingException, HtmlRenderException, DateFormatException {
		AssertUtility.notNull(htmlBuilder);
		
		HtmlSelect select = null;
		if(dataType != null && dataType.equalsIgnoreCase("time12")) {
			select = new HtmlSelect();
			select.setTypesetting("false");
			select.setBlankOptionFlag("false");
			Map<String, String> option = new LinkedHashMap<String, String>();
			option.put("am", webutil.getMessage("common.am"));
			option.put("pm", webutil.getMessage("common.pm"));
			webutil.setRequestAttribute("midOption", option);
			select.setOption("midOption");
			if(htmlId != null) {
				if(type == 1) {
					select.setOnChange("checkDataType(document.getElementById('" + htmlId + "_start'), '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
				} else {
					select.setOnChange("checkDataType(document.getElementById('" + htmlId + "_end'), '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
				}
			}
		}
		
		if(select != null) {
	        //設定各樣屬性
	        if(htmlId != null) {
	        	if(type == 1) {
	        		select.setId(htmlId + "_midStart");
	        	} else {
	        		select.setId(htmlId + "_midEnd");
	        	}
	        }
	        
	        if(name != null) {
	        	if(type == 1) {
	        		select.setName(name + "_midStart");
	        	} else {
	        		select.setName(name + "_midEnd");
	        	}
	        }
	        
	        boolean readOnlyFlag = false;
	        if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
	        	readOnlyFlag = true;
	        } else if(AssertUtility.isTrue(isReadOnly)) {
	        	readOnlyFlag = true;
	        }
	        if(readOnlyFlag) {
	        	select.setClassName("SelectMidReadOnly");
	        	isReadOnly = "true";
	        } else {
	        	select.setClassName("SelectMid");
	        	isReadOnly = "false";
	        }
	        
	        select.setIsReadOnly(isReadOnly);
	        
	        String strValue = "";
	        if(type == 1 && defaultValueStart != null) {
	        	strValue = defaultValueStart;
	        } else if(type == 2 && defaultValueEnd != null) {
	        	strValue = defaultValueEnd;
	        }
	        if(isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) {
	        	if(type == 1) {
		            if(webutil.getRequest().getParameter(name + "_start") != null) {
		            	strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name + "_start"));
		            }
	        	} else {
	        		if(webutil.getRequest().getParameter(name + "_end") != null) {
	        			strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name + "_end"));
		            }
	        	}
	        }
	        if((type == 1 && userValueStart != null) && webutil.getRequestAttribute(userValueStart) != null) {
	        	strValue = webutil.getRequestAttribute(userValueStart).toString();
	        } else if((type == 2 && userValueEnd != null) && webutil.getRequestAttribute(userValueEnd) != null) {
	        	strValue = webutil.getRequestAttribute(userValueEnd).toString();
	        }
	        if(dataType != null && !strValue.equals("")) {
	        	if(dataType.equalsIgnoreCase("time12") && htmlId != null) {
        			strValue = DateFormatUtility.completeHourMinuteSecond(strValue);
        			if(type == 1) {
            			if(!DateFormatUtility.compareTime(strValue, "11:59")) {
            				webutil.setRequestAttribute(htmlId + "_midOptionValueStart", "am");
            			} else {
            				webutil.setRequestAttribute(htmlId + "_midOptionValueStart", "pm");
            				strValue = StringUtility.addZero((Integer.parseInt(strValue.split(":")[0], 10) - 12), 2) + ":" + strValue.split(":")[1];
            			}
            			select.setUserValue(htmlId + "_midOptionValueStart");
        			} else if(type == 2) {
        				if(!DateFormatUtility.compareTime(strValue, "11:59")) {
            				webutil.setRequestAttribute(htmlId + "_midOptionValueEnd", "am");
            			} else {
            				webutil.setRequestAttribute(htmlId + "_midOptionValueEnd", "pm");
            				strValue = StringUtility.addZero((Integer.parseInt(strValue.split(":")[0], 10) - 12), 2) + ":" + strValue.split(":")[1];
            			}
            			select.setUserValue(htmlId + "_midOptionValueEnd");
        			}
            	}
	        }
	        
	        htmlBuilder.appendString(select.render());
		}
	}
	
	/**
	 * @param HtmlBuilder
	 * @param type(1:開始,2:結束)
	 * @author George_Tsai
	 * @version 2008/5/22
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 * @throws DateFormatException 
	 */
	private void renderBodyIndividual(HtmlBuilder htmlBuilder, int type) throws UnsupportedEncodingException, NamingException, DateFormatException {
		AssertUtility.notNull(htmlBuilder);
		
		htmlBuilder.inputStart().type("text");
        
        //設定各樣屬性
        if(htmlId != null) {
        	if(type == 1) {
        		htmlBuilder.id(htmlId + "_start");
        	} else {
        		htmlBuilder.id(htmlId + "_end");
        	}
        }
        
        if(name != null) {
        	if(type == 1) {
        		htmlBuilder.name(name + "_start");
        	} else {
        		htmlBuilder.name(name + "_end");
        	}
        }
        
        boolean readOnlyFlag = false;
        if(htmlId != null && AssertUtility.isTrue(webutil.getRequestAttribute(htmlId + "_IsReadOnly"))) {
        	readOnlyFlag = true;
        } else if(AssertUtility.isTrue(isReadOnly)) {
        	readOnlyFlag = true;
        }
        if(readOnlyFlag) {
        	htmlBuilder.readOnly();
        	isReadOnly = "true";
        } else {
        	isReadOnly = "false";
        }
        
        if(className != null) {
        	htmlBuilder.className(className);
        } else {
        	if(dataType == null || dataType.equalsIgnoreCase("string") || dataType.equalsIgnoreCase("function") || dataType.equalsIgnoreCase("password")) {
            	if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextReadOnly");
            	} else {
            		htmlBuilder.className("Text");
            	}
    		} else if(dataType.equalsIgnoreCase("email")) {
    			if(isReadOnly == null || isReadOnly.equalsIgnoreCase("false")) {
            		htmlBuilder.className("TextEmail");
            	} else {
            		htmlBuilder.className("TextEmailReadOnly");
            	}
    		} else if(dataType.equalsIgnoreCase("IDNO")) {
    			if(isReadOnly == null || isReadOnly.equalsIgnoreCase("false")) {
            		htmlBuilder.className("TextIDNO");
            	} else {
            		htmlBuilder.className("TextIDNOReadOnly");
            	}
    		} else if(dataType.equalsIgnoreCase("number") || dataType.equalsIgnoreCase("currency")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextCurrencyReadOnly");
            	} else {
            		htmlBuilder.className("TextCurrency");
            	}
    		} else if(dataType.toLowerCase().equalsIgnoreCase("date")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextDateReadOnly");
            	} else {
            		htmlBuilder.className("TextDate");
            	}
    		} else if(dataType.toLowerCase().startsWith("time")) {
    			if(isReadOnly == null || isReadOnly.equalsIgnoreCase("false")) {
            		htmlBuilder.className("TextTime");
            	} else {
            		htmlBuilder.className("TextTimeReadOnly");
            	}
    		} else if(dataType.toLowerCase().equalsIgnoreCase("datetime")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextDatetimeReadOnly");
            	} else {
            		htmlBuilder.className("TextDatetime");
            	}
    		} else if(dataType.equalsIgnoreCase("color")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextColorReadOnly");
            	} else {
            		htmlBuilder.className("TextColor");
            	}
    		} else if(dataType.equalsIgnoreCase("ip")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextIPReadOnly");
            	} else {
            		htmlBuilder.className("TextIP");
            	}
    		} else if(dataType.equalsIgnoreCase("ipv6")) {
    			if(isReadOnly.equalsIgnoreCase("true")) {
            		htmlBuilder.className("TextIPv6ReadOnly");
            	} else {
            		htmlBuilder.className("TextIPv6");
            	}
    		}
        }
        
        if(dataType != null) {
        	htmlBuilder.tagProperty("DataType", dataType.toLowerCase());
        	
        	if(AssertUtility.isTrue(isMasked)) {
            	if(dataType.equalsIgnoreCase("date")) {
            		String dateFormatString = DateFormatUtility.getDateFormat(Integer.parseInt(webutil.getSysDateFormat())).replaceAll("y", "9").replaceAll("M", "9").replaceAll("d", "9");
            		if(type == 1) {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_start\").mask(\"" + dateFormatString + "\");");
                	} else {
                		webutil.addPageLoadScript("$(\"#" + htmlId + "_end\").mask(\"" + dateFormatString + "\");");
                	}
            	} else if(dataType.equalsIgnoreCase("time12") || dataType.equalsIgnoreCase("time24")) {
            		if(type == 1) {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_start\").mask(\"99:99\");");
            		} else {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_end\").mask(\"99:99\");");
            		}
            	} else if(dataType.equalsIgnoreCase("datetime")) {
            		String dateFormatString = DateFormatUtility.getDateFormat(Integer.parseInt(webutil.getSysDateFormat())).replaceAll("y", "9").replaceAll("M", "9").replaceAll("d", "9");
            		if(type == 1) {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_start\").mask(\"" + dateFormatString + " 99:99\");");
            		} else {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_end\").mask(\"" + dateFormatString + " 99:99\");");
            		}
            	} else if(dataType.equalsIgnoreCase("ip")) {
            		webutil.addPageLoadScript("$.mask.definitions['2']='[ 12]';");
            		webutil.addPageLoadScript("$.mask.definitions['9']='[ 1234567890]';");
            		if(type == 1) {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_start\").mask(\"299.299.299.299\");");
            		} else {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_end\").mask(\"299.299.299.299\");");
            		}
            		htmlBuilder.tagProperty("Masked", "true");
            	} else if(dataType.equalsIgnoreCase("ipv6")) {
            		webutil.addPageLoadScript("$.mask.definitions['f']='[ 0123456789abcdef]';");
            		if(type == 1) {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_start\").mask(\"ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff\");");
            		} else {
            			webutil.addPageLoadScript("$(\"#" + htmlId + "_end\").mask(\"ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff\");");
            		}
            		htmlBuilder.tagProperty("Masked", "true");
            	}
        	}
        	
        	if(minvalue != null) {
        		htmlBuilder.tagProperty("minValue", minvalue);
        	} else {
        		htmlBuilder.tagProperty("minValue", "-1");
        	}
        	if(maxvalue != null) {
        		htmlBuilder.tagProperty("maxValue", maxvalue);
        	} else {
        		htmlBuilder.tagProperty("maxValue", "-1");
        	}
        	
        	if(maxinteger != null) {
          	htmlBuilder.tagProperty("maxInteger", maxinteger);
          } else {
          	htmlBuilder.tagProperty("maxInteger", "-1");
          }
            
          if(maxdecimal != null) {
          	htmlBuilder.tagProperty("maxDecimal", maxdecimal);
          } else {
          	htmlBuilder.tagProperty("maxDecimal", "-1");
          }
        } else {
        	htmlBuilder.tagProperty("DataType", "string");
        	htmlBuilder.tagProperty("minValue", "-1");
        	htmlBuilder.tagProperty("maxValue", "-1");
        	htmlBuilder.tagProperty("maxInteger", "-1");
        	htmlBuilder.tagProperty("maxDecimal", "-1");
        }
        
        String strIsRequired = "false";
        if(htmlId != null) {
	        if(webutil.getRequestAttribute(htmlId + "_IsRequired") != null) {
	        	if(webutil.getRequestAttribute(htmlId + "_IsRequired").toString().equals("true")) {
	        		strIsRequired = "true";
	        	}
	        } else {
	        	if(AssertUtility.isTrue(isRequired)) {
	            	strIsRequired = "true";
	            }
	        }
        } else {
        	if(AssertUtility.isTrue(isRequired)) {
            	strIsRequired = "true";
            }
        }
        
        htmlBuilder.tagProperty("isRequired", strIsRequired);
        
        String strValue = "";
        if(type == 1 && defaultValueStart != null) {
        	strValue = defaultValueStart;
        } else if(type == 2 && defaultValueEnd != null) {
        	strValue = defaultValueEnd;
        }
        if(isReturnValue == null || isReturnValue.equalsIgnoreCase("true")) {
        	if(type == 1) {
	            if(webutil.getRequest().getParameter(name + "_start") != null) {
	            	strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name + "_start"));
	            }
        	} else {
        		if(webutil.getRequest().getParameter(name + "_end") != null) {
        			strValue = StringUtility.cleanXSSPattern(webutil.getRequest().getParameter(name + "_end"));
	            }
        	}
        }
        if(type == 1 && userValueStart != null && webutil.getRequestAttribute(userValueStart) != null) {
        	strValue = webutil.getRequestAttribute(userValueStart).toString();
        } else if(type == 2 && userValueEnd != null && webutil.getRequestAttribute(userValueEnd) != null) {
        	strValue = webutil.getRequestAttribute(userValueEnd).toString();
        }
        if(dataType != null && !strValue.equals("")) {
        	if(dataType.equalsIgnoreCase("currency")) {
        		strValue = NumberFormatUtility.getCurrency(strValue);
        	} else if(dataType.equalsIgnoreCase("color")) {
        		strValue = "#ffffff";
        	} else if(dataType.equalsIgnoreCase("time12")) {
        		strValue = DateFormatUtility.completeHourMinuteSecond(strValue);
	        	if(DateFormatUtility.compareTime(strValue, "11:59")) {
	    			strValue = StringUtility.addZero((Integer.parseInt(strValue.split(":")[0], 10) - 12), 2) + ":" + strValue.split(":")[1];
	    		}
    		} else if(dataType.equalsIgnoreCase("time24")) {
    			strValue = DateFormatUtility.completeHourMinuteSecond(strValue);
    		}
        }
        htmlBuilder.value(strValue);
        
        if(maxlength != null) {
        	htmlBuilder.maxLength(maxlength);
        }
        
        if(fixlength != null) {
        	htmlBuilder.tagProperty("fixLength", fixlength);
        } else {
        	htmlBuilder.tagProperty("fixLength", "-1");
        }
        
        if(size != null) {
        	htmlBuilder.size(size);
        }
        
        if(style != null) {
        	htmlBuilder.style(style);
        }
        
        if(onChange != null) {
        	htmlBuilder.onChange(onChange);
        }else {
        	htmlBuilder.onChange("checkDataType(this, '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
        }
        
        if(onBlur != null) {
        	htmlBuilder.onBlur(onBlur);
        }
        
        if(onClick != null) {
        	htmlBuilder.onClick(onClick);
        } else {
        	if((isReadOnly == null || !isReadOnly.equalsIgnoreCase("true")) && op != null && op.equals(OpEnum.TEXT)) {
    			//如果是日期格式，提供onClick事件可以呼叫小月曆
    			if(dataType != null && dataType.toLowerCase().equalsIgnoreCase("date")) {
        			if(onChange != null) {
        				if(imagePath != null) {
        					htmlBuilder.onClick("openCalendar(this, " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
        				} else {
        					htmlBuilder.onClick("openCalendar(this, " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
        				}
        			} else {
        				if(imagePath != null) {
        					htmlBuilder.onClick("openCalendar(this, " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
        				} else {
        					htmlBuilder.onClick("openCalendar(this, " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
        				}
        			}
    			}
        		//如果是顏色格式，提供onClick事件可以呼叫色盤
        		if(dataType != null && dataType.equalsIgnoreCase("color")) {
        			if(type == 1) {
        				htmlBuilder.onClick("showColorPicker('" + htmlId + "_start');");
        			} else {
        				htmlBuilder.onClick("showColorPicker('" + htmlId + "_end');");
        			}
        		}
	        }
        }
        
        if(onKeyDown != null) {
        	htmlBuilder.onKeyDown(onKeyDown);
        } else {
        	if(isReadOnly.equalsIgnoreCase("false")) {
        		//如果是日期格式，提供onKeyDown事件可以收起小月曆
	    		if(dataType != null && dataType.toLowerCase().equalsIgnoreCase("date")) {
	    			htmlBuilder.onKeyDown("closeCalendar(this)");
	    		}
	    		//如果是currency格式,提供onKeyDown事件可以檢查有沒有輸入逗號
	    		if(dataType != null && dataType.equalsIgnoreCase("currency")) {
	    			if(!AssertUtility.isNotNullAndNotSpace(numberStep)) {
    	    			if(maxlength != null) {
    	    				htmlBuilder.onKeyDown("checkComma(this, " + maxlength + ")");
    	    			} else {
    	    				htmlBuilder.onKeyDown("checkComma(this, 0)");
    	    			}
	    			} else { //如果有設定numberStep,則要加入加減step功能
	    				String max = "-1";
    	    			String min = "-1";
    	    			if(AssertUtility.isNotNullAndNotSpace(maxvalue)) {
    	    				max = maxvalue;
    	    			}
    	    			if(AssertUtility.isNotNullAndNotSpace(minvalue)) {
    	    				min = minvalue;
    	    			}
    	    			
	    				if(maxlength != null) {
    	    				htmlBuilder.onKeyDown("checkComma(this, " + maxlength + ");stepNumber(this, event, " + numberStep + ", " + max + ", " + min + ");");
    	    			} else {
    	    				htmlBuilder.onKeyDown("checkComma(this, 0);stepNumber(this, event, " + numberStep + ", " + max + ", " + min + ");");
    	    			}
	    			}
    			}
	    		
	    		//如果是number格式且有設定numberStep,則要加入加減step功能
	    		if(dataType != null && dataType.equalsIgnoreCase("number") && AssertUtility.isNotNullAndNotSpace(numberStep)) {
	    			String max = "-1";
	    			String min = "-1";
	    			if(AssertUtility.isNotNullAndNotSpace(maxvalue)) {
	    				max = maxvalue;
	    			}
	    			if(AssertUtility.isNotNullAndNotSpace(minvalue)) {
	    				min = minvalue;
	    			}
	    			htmlBuilder.onKeyDown("stepNumber(this, event, " + numberStep + ", " + max + ", " + min + ");");
	    		}
	    		
	    		//如果是顏色格式，提供onKeyDown事件可以收起色盤
	    		if(dataType != null && dataType.equalsIgnoreCase("color")) {
	    			if(type == 1) {
	    				htmlBuilder.onKeyDown("hideColorPicker('" + htmlId + "_start')");
	    			} else {
	    				htmlBuilder.onKeyDown("hideColorPicker('" + htmlId + "_end')");
	    			}
	    		}
        	}
        }
    	
        htmlBuilder.tagClose();
        htmlBuilder.inputEnd();
        
        //如果是日期格式，提供按鈕可以呼叫小月曆
        //如果是顏色格式，提供按鈕可以呼叫色盤
        //如果是需呼叫特殊功能來填值(function)，提供按鈕可以呼叫function
        if(isReadOnly.equalsIgnoreCase("false")) {
	        if(dataType != null && dataType.toLowerCase().equalsIgnoreCase("date")) {
	        	if(op == null || op.equals(OpEnum.BUTTON)) {
	        		htmlBuilder.space();
	//	            htmlBuilder.buttonStart().type("button");
	        		if(imagePath != null) {
	        			if(style != null) {
	        				htmlBuilder.imageStart().src(imagePath + "/calendar_icon.gif").style("vertical-align:top;cursor:pointer;" + style);
	        			} else {
	        				htmlBuilder.imageStart().src(imagePath + "/calendar_icon.gif").style("vertical-align:top;cursor:pointer;");
	        			}
	        		} else {
	        			if(style != null) {
	        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/calendar_icon.gif").style("vertical-align:top;cursor:pointer;" + style);
	        			} else {
	        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/calendar_icon.gif").style("vertical-align:top;cursor:pointer;");
	        			}
	        		}
		            if(buttonClass != null) {
		    			htmlBuilder.className(buttonClass);
		    		}
		            if(type == 1) {
		            	if(htmlId != null) {
		        			htmlBuilder.id(htmlId + "_startButton");
		        		}
		            	if(onChange != null) {
		            		if(imagePath != null) {
		            			htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_start'), " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		            		} else {
		            			htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_start'), " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		            		}
		            	} else {
		            		if(imagePath != null) {
		            			htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_start'), " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		            		} else {
		            			htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_start'), " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		            		}
		            	}
		    		} else {
		    			if(htmlId != null) {
		        			htmlBuilder.id(htmlId + "_endButton");
		        		}
		    			if(onChange != null) {
		    				if(imagePath != null) {
		    					htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_end'), " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		    				} else {
		    					htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_end'), " + webutil.getEnvVariable("defaultDateFormat") + ", '" + onChange.replaceAll("'", "&quote") + "', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		    				}
		    			} else {
		    				if(imagePath != null) {
		    					htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_end'), " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + imagePath + "', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		    				} else {
		    					htmlBuilder.onClick("openCalendar(document.getElementById('" + htmlId + "_end'), " + webutil.getEnvVariable("defaultDateFormat") + ", '', '" + webutil.getBasePathForHTML() + "images', '" + webutil.getMessage("common.warning") + "', '" + webutil.getMessage("common.ok") + "', '" + webutil.getMessage("common.error.interval") + "', '" + webutil.getMessage("common.error.DataType1Start") + "', '" + webutil.getMessage("common.error.DataType1End") + "', '" + webutil.getMessage("common.error.DataType2") + "', '" + webutil.getMessage("common.error.DataType3") + "', '" + webutil.getMessage("common.error.DataType4") + "', '" + webutil.getMessage("common.error.DataType5") + "', '" + webutil.getMessage("common.error.DataType6") + "', '" + webutil.getMessage("common.error.DataType7") + "', '" + webutil.getMessage("common.error.DataType8Start") + "', '" + webutil.getMessage("common.error.DataType8End") + "', '" + webutil.getMessage("common.error.DataType9Start") + "', '" + webutil.getMessage("common.error.DataType9End") + "', '" + webutil.getMessage("common.error.DataType10Start") + "', '" + webutil.getMessage("common.error.DataType10End") + "', '" + webutil.getMessage("common.error.DataType11") + "', '" + webutil.getMessage("common.error.DataType12Start") + "', '" + webutil.getMessage("common.error.DataType12End") + "', '" + webutil.getMessage("common.error.DataType13") + "', '" + webutil.getMessage("common.error.DataType14") + "');");
		    				}
		    			}
		    		}
		            htmlBuilder.tagClose();
	//	            htmlBuilder.text("...");
	//	            htmlBuilder.buttonEnd();
		            htmlBuilder.imageEnd();
	        	}
	        	//產生小月曆的DIV
	        	if(type == 1) {
	        		htmlBuilder.divStart().id(htmlId + "_start_calendar");
	        	} else {
	        		htmlBuilder.divStart().id(htmlId + "_end_calendar");
	        	}
	        	htmlBuilder.className("calendar");
	    		htmlBuilder.style("position:absolute;left:0;top:0; z-index:5;display:none;");
	    		htmlBuilder.tagClose();
	    		htmlBuilder.divEnd();
	        } else if(dataType != null && dataType.equalsIgnoreCase("color")) {
	        	if(op == null || op.equals(OpEnum.BUTTON)) {
	        		htmlBuilder.space();
	//	            htmlBuilder.buttonStart().type("button");
	        		if(imagePath != null) {
	        			if(style != null) {
	        				htmlBuilder.imageStart().src(imagePath + "/colorize.png").style("vertical-align:top;cursor:pointer;width:22px;height:22px" + style);
	        			} else {
	        				htmlBuilder.imageStart().src(imagePath + "/colorize.png").style("vertical-align:top;cursor:pointer;width:22px;height:22px");
	        			}
	        		} else {
	        			if(style != null) {
	        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/colorize.png").style("vertical-align:top;cursor:pointer;width:22px;height:22px" + style);
	        			} else {
	        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/colorize.png").style("vertical-align:top;cursor:pointer;width:22px;height:22px");
	        			}
	        		}
		            if(buttonClass != null) {
		    			htmlBuilder.className(buttonClass);
		    		}
		            if(type == 1) {
		            	if(htmlId != null) {
		        			htmlBuilder.id(htmlId + "_startButton");
		        		}
		            	htmlBuilder.onClick("showColorPicker('" + htmlId + "_start');");
		    		} else {
		    			if(htmlId != null) {
		        			htmlBuilder.id(htmlId + "_endButton");
		        		}
		    			htmlBuilder.onClick("showColorPicker('" + htmlId + "_end');");
		    		}
		            htmlBuilder.tagClose();
	//	            htmlBuilder.text("...");
	//	            htmlBuilder.buttonEnd();
		            htmlBuilder.imageEnd();
	        	}
	        	//產生色盤的DIV
	        	if(type == 1) {
	        		htmlBuilder.divStart().id(htmlId + "_start_colorPicker");
	        		webutil.addPageLoadScript("$('#" + htmlId + "_start_colorPicker').farbtastic('#" + htmlId + "_start');");
	        	} else {
	        		htmlBuilder.divStart().id(htmlId + "_end_colorPicker");
	        		webutil.addPageLoadScript("$('#" + htmlId + "_end_colorPicker').farbtastic('#" + htmlId + "_end');");
	        	}
	        	htmlBuilder.style("position:absolute;left:0;top:0; z-index:5;display:none;");
	    		htmlBuilder.tagClose();
	    		htmlBuilder.divEnd();
	    	} else if(dataType != null && dataType.equalsIgnoreCase("function")) {
	        	htmlBuilder.space();
//	        	htmlBuilder.buttonStart().type("button");
	        	if(imagePath != null) {
	        		if(style != null) {
	        			htmlBuilder.imageStart().src(imagePath + "/search_icon.gif").style("vertical-align:top;cursor:pointer;" + style);
	        		} else {
	        			htmlBuilder.imageStart().src(imagePath + "/search_icon.gif").style("vertical-align:top;cursor:pointer;");
	        		}
        		} else {
        			if(style != null) {
        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/search_icon.gif").style("vertical-align:top;cursor:pointer;" + style);
        			} else {
        				htmlBuilder.imageStart().src(webutil.getBasePathForHTML() + "images/search_icon.gif").style("vertical-align:top;cursor:pointer;");
        			}
        		}
	        	if(buttonClass != null) {
	    			htmlBuilder.className(buttonClass);
	    		}
	        	if(type == 1) {
	        		if(htmlId != null) {
	        			htmlBuilder.id(htmlId + "_startButton");
	        		}
	        		htmlBuilder.onClick(htmlId + "_start_onClick(document.getElementById('" + htmlId + "_start'));");
	        	} else {
	        		if(htmlId != null) {
	        			htmlBuilder.id(htmlId + "_endButton");
	        		}
	        		htmlBuilder.onClick(htmlId + "_end_onClick(document.getElementById('" + htmlId + "_end'));");
	        	}
	        	htmlBuilder.tagClose();
//	            htmlBuilder.text("...");
//	            htmlBuilder.buttonEnd();
	        	htmlBuilder.imageEnd();
	        }
        }
	}
	
	private void renderFooter(HtmlBuilder htmlBuilder) {
		AssertUtility.notNull(htmlBuilder);
		
		if(footer != null) {
            htmlBuilder.labelStart();
            if(htmlId != null) {
            	htmlBuilder.id(htmlId + "_footer");
            }
            if(name != null) {
            	htmlBuilder.name(name + "_footer");
            }
            if(footerClass != null) {
            	htmlBuilder.className(footerClass);
            } else {
            	htmlBuilder.className("LabelFooter");
            }
            htmlBuilder.tagClose();
            htmlBuilder.text(footer);
            htmlBuilder.labelEnd();
        }
	}
	
	//元件屬性區
	public void setId(String id) {
		this.htmlId = id;
	}
	
	public String getId() {
		return this.htmlId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setIsReadOnly(String readOnly) {
		this.isReadOnly = readOnly;
	}
	
	public String getIsReadOnly() {
		return this.isReadOnly;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public void setIsReturnValue(String returnValue) {
		this.isReturnValue = returnValue;
	}
	
	public String getIsReturnValue() {
		return this.isReturnValue;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDataType() {
		return this.dataType;
	}
	
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public String getOnChange() {
		return this.onChange;
	}
	
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	
	public String getOnClick() {
		return this.onClick;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}
	
	public String getHeaderClass() {
		return this.headerClass;
	}
	
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public String getFooter() {
		return this.footer;
	}
	
	public void setFooterClass(String footerClass) {
		this.footerClass = footerClass;
	}
	
	public String getFooterClass() {
		return this.footerClass;
	}
	
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}
	
	public String getButtonClass() {
		return this.buttonClass;
	}
	
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	public String getIsRequired() {
		return this.isRequired;
	}
	
	public void setDefaultValueStart(String defaultValue) {
		this.defaultValueStart = defaultValue;
	}
	
	public String getDefaultValueStart() {
		return this.defaultValueStart;
	}
	
	public void setDefaultValueEnd(String defaultValue) {
		this.defaultValueEnd = defaultValue;
	}
	
	public String getDefaultValueEnd() {
		return this.defaultValueEnd;
	}
	
	public void setUserValueStart(String value) {
		this.userValueStart = value;
	}
	
	public String getUserValueStart() {
		return this.userValueStart;
	}
	
	public void setUserValueEnd(String value) {
		this.userValueEnd = value;
	}
	
	public String getUserValueEnd() {
		return this.userValueEnd;
	}
	
	public void setOp(OpEnum value) {
		this.op = value;
	}
	
	public OpEnum getOp() {
		return this.op;
	}
	
	public void setMaxlength(String value) {
		this.maxlength = value;
	}
	
	public String getMaxlength() {
		return this.maxlength;
	}
	
	public void setFixlength(String value) {
		this.fixlength = value;
	}
	
	public String getFixlength() {
		return this.fixlength;
	}
	
	public void setSize(String value) {
		this.size = value;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public void setStyle(String value) {
		this.style = value;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public void setMaxvalue(String value) {
		this.maxvalue = value;
	}
	
	public String getMaxvalue() {
		return this.maxvalue;
	}
	
	public void setMinvalue(String value) {
		this.minvalue = value;
	}
	
	public String getMinvalue() {
		return this.minvalue;
	}
	
	public void setMaxinteger(String value) {
		this.maxinteger = value;
	}
	
	public String getMaxinteger() {
		return this.maxinteger;
	}
	
	public void setMaxdecimal(String value) {
		this.maxdecimal = value;
	}
	
	public String getMaxdecimal() {
		return this.maxdecimal;
	}
	
	public void setImagePath(String value) {
		this.imagePath = StringUtility.removeStartEndSlash(value);
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public void setTypesetting(String value) {
		this.typesetting = value;
	}
	
	public String getTypesetting() {
		return this.typesetting;
	}

	public void setOnKeyDown(String onKeyDown) {
		this.onKeyDown = onKeyDown;
	}

	public String getOnKeyDown() {
		return onKeyDown;
	}

	public void setIsMasked(String isMasked) {
		this.isMasked = isMasked;
	}

	public String getIsMasked() {
		return isMasked;
	}

	public void setNumberStep(String numberStep) {
		this.numberStep = numberStep;
	}

	public String getNumberStep() {
		return numberStep;
	}
}
