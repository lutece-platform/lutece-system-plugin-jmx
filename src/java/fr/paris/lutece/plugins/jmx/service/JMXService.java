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
package fr.paris.lutece.plugins.jmx.service;

import fr.paris.lutece.plugins.jmx.mbeans.MBeanManager;
import fr.paris.lutece.plugins.jmx.mbeans.ManagedResource;
import fr.paris.lutece.plugins.jmx.mbeans.ResourceManager;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.lang.management.ManagementFactory;

import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.RequiredModelMBean;


/**
 * JMX Service
 */
public class JMXService
{
    private static final String MANAGED_RESOURCE_TYPE = "objectReference";
    private MBeanServer _mbs = ManagementFactory.getPlatformMBeanServer(  );
    private List<ResourceManager> _listResourceManagers;
    private List<MBeanManager> _listBeanManagers;

    /**
     * Sets the MBean Managers List (injected)
     * @param list The MBean Managers List 
     */
    public void setBeanManagersList( List<MBeanManager> list )
    {
        _listBeanManagers = list;
    }

    /**
     * Sets the Resource MBeans Managers List (injected)
     * @param list The Resource MBeans Managers List 
     */
    public void setResourceManagersList( List<ResourceManager> list )
    {
        _listResourceManagers = list;
    }

    /**
     * Register MBeans
     */
    public void registerMBeans(  )
    {
        try
        {
            for ( MBeanManager bm : _listBeanManagers )
            {
                registerMBean( bm.getMBean(  ), bm.getMBeanName(  ) );
            }

            for ( ResourceManager rm : _listResourceManagers )
            {
                for ( ManagedResource mr : rm.getMBeans(  ) )
                {
                    RequiredModelMBean modelMBean = new RequiredModelMBean( rm.getMBeanInfo(  ) );
                    modelMBean.setManagedResource( mr, MANAGED_RESOURCE_TYPE );
                    registerMBean( modelMBean, mr.getName(  ) );
                }
            }
        }
        catch ( NullPointerException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( MBeanRegistrationException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( MBeanException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( RuntimeOperationsException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( InstanceNotFoundException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( InvalidTargetObjectTypeException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
    }

    /**
     * Register a MBean
     * @param mbean The MBean
     * @param strName The MBean name
     * @return true if registred
     */
    private boolean registerMBean( Object mbean, String strName )
    {
        try
        {
            ObjectName name = new ObjectName( strName );

            if ( !_mbs.isRegistered( name ) )
            {
                _mbs.registerMBean( mbean, name );
                AppLogService.info( "JMX Registering MBean : " + strName );

                return true;
            }
        }
        catch ( MalformedObjectNameException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( NullPointerException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( InstanceAlreadyExistsException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( MBeanRegistrationException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( NotCompliantMBeanException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( MBeanException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }
        catch ( RuntimeOperationsException e )
        {
            AppLogService.error( "JMX registering error : " + e.getMessage(  ), e );
        }

        return false;
    }
}
