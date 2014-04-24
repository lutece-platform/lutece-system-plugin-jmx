/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.jmx.mbeans.caches;

import fr.paris.lutece.util.jmx.mbeans.ManagedResource;
import fr.paris.lutece.util.jmx.mbeans.ResourceExporter;
import fr.paris.lutece.portal.service.cache.CacheService;
import fr.paris.lutece.portal.service.cache.CacheableService;

import java.util.ArrayList;
import java.util.List;

import javax.management.Descriptor;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;


/**
 * Cache MBeans Manager
 */
public class CacheMBeansExporter implements ResourceExporter
{
    /**
     * {@inheritDoc }
     */
    @Override
    public ModelMBeanInfo getMBeanInfo(  )
    {
        Descriptor descriptorInfos = new DescriptorSupport( new String[]
                {
                    "name=Infos", "descriptorType=attribute", "default=0", "displayName=Infos of the plugin",
                    "getMethod=getInfos", "setMethod=setInfos"
                } );

        Descriptor descriptorSize = new DescriptorSupport( new String[]
                {
                    "name=Size", "descriptorType=attribute", "displayName=Size of the plugin", "getMethod=getSize"
                } );

        Descriptor descriptorMemorySize = new DescriptorSupport( new String[]
                {
                    "name=MemorySize", "descriptorType=attribute", "displayName=Cache size", "getMethod=getMemorySize"
                } );

        Descriptor descriptorEnabled = new DescriptorSupport( new String[]
                {
                    "name=Enabled", "descriptorType=attribute", "displayName=Enabled", "getMethod=isEnabled"
                } );

        ModelMBeanAttributeInfo[] mmbai = new ModelMBeanAttributeInfo[4];
        mmbai[0] = new ModelMBeanAttributeInfo( "Infos", "java.lang.String", "Cache config", true, false, false,
                descriptorInfos );
        mmbai[1] = new ModelMBeanAttributeInfo( "Size", "java.lang.Integer", "Cache size", true, false, false,
                descriptorSize );
        mmbai[2] = new ModelMBeanAttributeInfo( "MemorySize", "java.lang.Long", "Cache memory size", true, false, false,
                descriptorMemorySize );
        mmbai[3] = new ModelMBeanAttributeInfo( "Enabled", "java.lang.Boolean", "Enabled", true, false, false,
                descriptorEnabled );

        ModelMBeanOperationInfo[] mmboi = new ModelMBeanOperationInfo[4];

        mmboi[0] = new ModelMBeanOperationInfo( "getInfos", "Infos getter", null, "String",
                ModelMBeanOperationInfo.INFO );

        mmboi[1] = new ModelMBeanOperationInfo( "getSize", "Size getter", null, "int",
                ModelMBeanOperationInfo.INFO );

        mmboi[2] = new ModelMBeanOperationInfo( "getMemorySize", "MemorySize getter", null, "long",
                ModelMBeanOperationInfo.INFO );

        mmboi[3] = new ModelMBeanOperationInfo( "isEnabled", "Enabled getter", null, "boolean",
                ModelMBeanOperationInfo.INFO );

        return new ModelMBeanInfoSupport( CacheManagedResource.class.getName(), "Caches", mmbai, null, mmboi, null );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<ManagedResource> getMBeans(  )
    {
        List<ManagedResource> list = new ArrayList<ManagedResource>(  );

        for ( CacheableService cs : CacheService.getCacheableServicesList(  ) )
        {
            list.add( new CacheManagedResource( cs ) );
        }

        return list;
    }
}
