/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.jmx.mbeans.plugins;

import fr.paris.lutece.util.jmx.mbeans.ManagedResource;
import fr.paris.lutece.util.jmx.mbeans.ResourceExporter;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import java.util.ArrayList;
import java.util.List;

import javax.management.Descriptor;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;


/**
 * Plugin MBeans Manager
 */
public class PluginMBeansExporter implements ResourceExporter
{
    /**
     * {@inheritDoc }
     */
    @Override
    public ModelMBeanInfo getMBeanInfo(  )
    {
        Descriptor descriptorVersion = new DescriptorSupport( new String[]
                {
                    "name=Version", "descriptorType=attribute", "default=0", "displayName=Version of the plugin",
                    "getMethod=getVersion", "setMethod=setVersion"
                } );

        Descriptor descriptorProvider = new DescriptorSupport( new String[]
                {
                    "name=Provider", "descriptorType=attribute", "displayName=Provider of the plugin",
                    "getMethod=getProvider"
                } );

        ModelMBeanAttributeInfo[] mmbai = new ModelMBeanAttributeInfo[2];
        mmbai[0] = new ModelMBeanAttributeInfo( "Version", "java.lang.String", "Version of the plugin", true, false,
                false, descriptorVersion );
        mmbai[1] = new ModelMBeanAttributeInfo( "Provider", "java.lang.String", "Provider of the plugin", true, false,
                false, descriptorProvider );

        ModelMBeanOperationInfo[] mmboi = new ModelMBeanOperationInfo[2];

        mmboi[0] = new ModelMBeanOperationInfo( "getVersion", "getter for Version attribute", null, "String",
                ModelMBeanOperationInfo.INFO );

        mmboi[1] = new ModelMBeanOperationInfo( "getProvider", "getter for Provider attribute", null, "String",
                ModelMBeanOperationInfo.INFO );

        return new ModelMBeanInfoSupport( PluginManagedResource.class.getName(), "PluginModelBean", mmbai, null, mmboi, null );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<ManagedResource> getMBeans(  )
    {
        List<ManagedResource> list = new ArrayList<ManagedResource>(  );

        for ( Plugin plugin : PluginService.getPluginList(  ) )
        {
            list.add( new PluginManagedResource( plugin ) );
        }

        return list;
    }
}
