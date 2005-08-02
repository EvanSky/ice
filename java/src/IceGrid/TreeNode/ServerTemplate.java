// **********************************************************************
//
// Copyright (c) 2003-2005 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
package IceGrid.TreeNode;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

import IceGrid.IceBoxDescriptor;
import IceGrid.Model;
import IceGrid.ServerDescriptor;
import IceGrid.TemplateDescriptor;
import IceGrid.TreeModelI;
import IceGrid.ServerDynamicInfo;
import IceGrid.TemplateDescriptor;
import IceGrid.Utils;


class ServerTemplate extends Parent
{
    
    //
    // Application is needed to lookup service templates
    //
    ServerTemplate(String name, TemplateDescriptor descriptor, Application application)
    {
	super(name, application.getModel());
	rebuild(descriptor, application);
    }

    void rebuild(TemplateDescriptor descriptor, Application application)
    {
	_descriptor = descriptor;
	clearChildren();

	//
	// Fix-up parameters order
	//
	java.util.Collections.sort(_descriptor.parameters);
	
	if(_descriptor.descriptor instanceof IceBoxDescriptor)
	{
	    _iceBoxDescriptor = (IceBoxDescriptor)_descriptor.descriptor;
	    
	    _services = new Services(_iceBoxDescriptor.services, true, null, application);
	    addChild(_services);
	}
	else
	{
	    _services = null;
	    _iceBoxDescriptor = null;
	}
	
	_adapters = new Adapters(_descriptor.descriptor.adapters, true, 
				 null, _model);
	addChild(_adapters);

	_dbEnvs = new DbEnvs(_descriptor.descriptor.dbEnvs, true,
			     null, _model);
	addChild(_dbEnvs);
    }


    public String toString()
    {
	return templateLabel(_id, _descriptor.parameters);
    }

    public TemplateDescriptor getDescriptor()
    {
	return _descriptor;
    }

    private TemplateDescriptor _descriptor;
    private IceBoxDescriptor _iceBoxDescriptor;

    private Services _services;
    private Adapters _adapters;
    private DbEnvs _dbEnvs;
}
