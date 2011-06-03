/**
 * Copyright (c) 2010 George Tsai. All rights reserved.
 * For more information on the George Tsai, please see http://www.pixnet.net/blog/csiebug
 */
package csiebug.domain.hibernateImpl;

import csiebug.domain.ResourceType;

/**
 * @author George_Tsai
 * @version 2011/2/23
 */
public class ResourceTypeEnumUserType extends GenericEnumUserType<ResourceType> {
	public ResourceTypeEnumUserType() {
		super(ResourceType.class);
	}
}
