/**
 * ================================================================================
 * ||工具包，包含对应 EXT 的一些常用工具。
 * author LLJ  by 2010 09 28
 * ================================================================================
 */
ExtTools = {
	version : "3.0",
	author:'LLJ',
	since: 'September 28 2010',
	update:'July 15 2011'

};


/*
 * ==================================================================================
 * 根据ID获取相对应的 * Ext对象的简便封装，这些对象必须是定义过的才能够获取到。
 * ----------------------------------------------------------------------------------
 * start Utils.get
 */

/**
 * 根据 ID 获取一个 Store 对象，这个 Store 必须定义过。
 */
ExtTools.getStore = function(/* String */id) {
	if (id == null || id == "") {
		Ext.Msg.alert("提示", "getAStore -- id 不能为空！！！");
		return null;
	}
	return Ext.StoreMgr.lookup(id);
};


/**
 * 根据 URL 获取列的数组。
 *
 * @param  url
 * @return 响应文本对象，使用 array.xxx.xxx.xxx.xxx的方式进行获取。
 */
ExtTools.getResponseJson = function(url, params) {

    var xmlRequest = Ext.lib.Ajax.getConnectionObject().conn;
    var param = "";
    if (params != null && params != "") {
        param = "?" + Ext.urlEncode(params);
    }
    xmlRequest.open('POST', url + param, false);
    xmlRequest.send(null);
    var Json = Ext.util.JSON.decode(xmlRequest.responseText);
    return Json;
};

/*
 * ==================================================================================
 * 根据条件快捷的创建各类 EXT 对象的封装。
 * ----------------------------------------------------------------------------------
 * ExtTools.create
 */

/**
 * 创建一个 store 并返回该Store
 * @param o 属性如下：
 * o.id 一个 store 的id string
 * o.isAutoLoad 是否自动加载 Boolean
 * o.url l 请求的 url  string
 * o.reader 读取的 reader
 * o.baseParams 基本参数
 * @return {} store
 */
 /*例子：针对ComboBox数据
 typeComStoreConfig={
	id:'typeComStore_id',
	url:'typeManageAction!getTypeComStore.do',
	reader:new Ext.data.JsonReader({
	            root: 'items',
	            fields: [{
	                name: 'NAME'
	            },
	            {
	                name:'ID'
	            }]
	        }),
  // isAutoLoad:true//默认为false(不配置为false)
}
*/
ExtTools.createStore = function(o) {
	if (o.id == null || o.id == "") {
		Ext.Msg.alert("提示", "store id 不能为空！！！");
		return null;
	}
	if (o.isAutoLoad == null||o.isAutoLoad == "") {
		o.isAutoLoad = false;
	}
	if (o.baseParams == null || o.baseParams == "") {
		o.baseParams = {
			// test参数，用来测试，无实际意义
			test : ""
		};
	};
	if (o.reader == null || o.reader == "") {
		//o.reader = '';
	};
	if (o.url == null || o.url == "") {
		o.url='/';
	};
    var ds=new Ext.data.Store({
        id: o.id,
        proxy: new Ext.data.HttpProxy({
            url: o.url
        }),
        remoteSort: true,
        baseParams : o.baseParams,
		autoLoad : o.isAutoLoad,
        reader: o.reader

    });
    return ds;
};



/**
 * 创建一个 window。。
 * @param {} o 属性如下
 * @param {} o.id
 * @param {} o.title 标题
 * @param {} o.layout 布局
 * @param {} o.width 宽
 * @param {} o.height 高
 * @param {} o.resizable,
 * @param {} o.closeAction,
 * @param {} o.closable,
 * @param {} o.plain
 * @param {} o.bodyStyle
 * @param {} o.buttonAlign
 * @param {} o.modal 是否遮盖
 * @param {} o.items 内置组件
 * @param {} o.buttons 按钮
 * @return {} win
 */
/*例子：
 var upwincf = {
                id: 'uploadWinId',
                title: '背景上传',
                layout: 'fit',
                width: 300,
                height: 200,
                bodyStyle: 'padding:1px 1px 1px 1px',
                items: [upfrm],
                buttons: [{
                    text: "确定",
                    iconCls: 'ok',
                    handler: function () {
                		desme.uploadFormSubmit();
                    }
                }]

            }
 var upwin = ExtTools.createWindow(upwincf);
 */
ExtTools.createWindow = function(o) {
    if (o.id == null || o.id == "") {
		Ext.Msg.alert("提示", "store id 不能为空！！！");
		return null;
	};
	if(o.title==null||o.title==""){
	   o.title='';
	};
	if(o.layout==null||o.layout==""){
	   o.layout='fit';
	};
	if (o.width == null || o.width == "") {
		width = 600;
	};
	if (o.height == null || o.height == "") {
		height = "auto";
	};
	if (o.modal == null || o.modal == "") {
		o.modal = true;
	};
	if (o.resizable == null || o.resizable == "") {
		o.resizable = false;
	};
	if (o.closeAction == null || o.closeAction == "") {
		o.closeAction = 'close';
	};
	if (o.closable == null || o.closable == "") {
		o.closable = true;
	};
	if (o.plain == null || o.plain == "") {
		o.plain = true;
	};
	if (o.bodyStyle == null || o.bodyStyle == "") {
		o.bodyStyle = "padding:0px";
	};
	if (o.buttonAlign == null || o.buttonAlign == "") {
		o.buttonAlign = 'center';
	};
	if (o.items == null || o.items == "") {
		o.items = [];
	};
	if (o.buttons != "" && o.buttons != null) {
        o.buttons[o.buttons.length] = {
            text: "关闭",
            iconCls:'close',
            handler: function () {
                win.close();
            }
        };
    } else {
        o.buttons = [{
            text: "关闭",
            iconCls:'close',
            handler: function () {
                win.close();
            }
        }];
    };
    var win = Ext.getCmp(o.id);
    if (!win) {
        win = new Ext.Window({
            id: o.id,
            title: o.title,
            layout: o.layout,
            width: o.width,
            height: o.height,
            resizable: o.resizable,
            closeAction: o.closeAction,
            closable: o.closable,
            modal: o.modal,
            plain: o.plain,
            bodyStyle: o.bodyStyle,
            buttonAlign: o.buttonAlign,
            items: o.items,
            buttons: o.buttons
        });
    } else {
        win.close();
    };
    return win;
};
/**
 * 创建一个ComboBox
 *
 * @param {}  o 对象属性如下
 * @param {}  o.id
 * @param {}  o.store
 * @param {}  o.valueField 真实值
 * @param {}  o.displayField 显示内容
 * @param {}  o.hiddenName 提交id
 * @param {}  o.allowBlank 默认是否可谓空
 * @param {}  o.blankText 验证提示
 * @param {}  o.emptyText 为空提示
 * @param {}  o.value 预设值
 * @param {}  o.label 标题
 * @param {}  o.msgTarget 提示信息
 * @param {}  o.listeners 事件
 */

ExtTools.createComboBox =function(o){
     if(o.id==null||o.id==''){
     	Ext.Msg.alert("提示", "combox id 不能为空！！！");
		return null;
     };
     if(o.valueField==null||o.valueField==""){
        o.valueField='ID';
     };
     if(o.displayField==null||o.displayField==""){
        o.displayField='NAME';
     };
     if(o.hiddenName==null||o.hiddenName==""){
     	o.hiddenName='';
     };

     if (o.store == null || o.store == "") {
		//Ext.Msg.alert("提示", "combox -- 获取 Store 的不能为空！！！");
		//return null;
		o.store='';
	 };

     if(o.allowBlank==null||o.allowBlank==""){
     	o.allowBlank=true;
     };
     if(o.blankText==null||o.blankText==""){
     	o.blankText='';
     };
     if(o.emptyText==null||o.emptyText==""){
     	o.emptyText='下拉选择';
     };
     if(o.value==null||o.value==""){
     	o.value='';
     };
     if(o.label==null||o.label==""){
     	o.label='';
     };
     if(o.msgTarget==null||o.msgTarget==""){
     	o.msgTarget='side';
     };
     if(o.anchor==null||o.anchor==""){
     	o.anchor='100%';
     };
     if(o.listeners==null||o.listeners==""){
     	o.listeners='';
     };
     if(o.labelWidth==null||o.labelWidth==""){
     	o.labelWidth=120;
     };
     if(o.hideLabel==null||o.hideLabel==''){
        o.hideLabel=false;
     };
     var combox=new Ext.form.ComboBox({
        xtype: 'combo',
        id: o.id,
        store: o.store,
        valueField: o.valueField,
        displayField: o.displayField,
        hiddenName : o.hiddenName,
        mode: 'local',
        allowBlank: o.allowBlank,
        blankText: o.blankText,
        forceSelection: true,
        emptyText: o.emptyText,
        editable: false,
        value: o.value,
        triggerAction: 'all',
        fieldLabel: o.label,
        selectOnFocus: true,
       // msgTarget: o.msgTarget,
        anchor: o.anchor,
        labelWidth: o.labelWidth,
        anchor: o.anchor,
        hideLabel:o.hideLabel,
        listeners: o.listeners
        });

        return combox;
};

/**
 * 创建fieldset
 *
 * @param {} id
 * @param {} title
 * @param {} items
 * @return {}
 */
ExtTools.createFieldSet = function(id, title, items, layout) {
	if (id == "" || id == null) {
		Ext.Msg.alert("提示", "ExtTools.createGridPanel: id 不能为空<br>本信息仅用于调试 - Ousui");
		return null;
	}
	if (items != "" && items != null && items.length == null) {
		Ext.Msg.alert("提示", "items 应该为数组，且为 form 的类！！<br>本信息仅用于调试 - Ousui");
		return null;
	}
	if (layout == "" || layout == null) {
		layout = "form";
	}
	var s = new Ext.form.FieldSet({
				id : id + ExtTools.idSuffix.fieldSet,
				title : title,
				height : 'auto',
				autoWidth : true,
				layout : layout,
				items : items
			});
	return s;
};

/**
 *
 * @param {} id
 * @param {} name
 * @param {} lable
 * @param {} type
 * @param {} hidden
 * @param {} readOnly
 * @param {} event
 * @return {}
 */
ExtTools.createField = function(id, name, label, xtype, hidden, readOnly, event) {
	if (id == "" || id == null) {
		Ext.Msg.alert("提示", "ExtTools.createField: id 不能为空！！<br>本信息仅用于调试 - Ousui");
		return null;
	}
	if (hidden == null || hidden == "") {
		hidden = false;
	}
	var format = 'Y-m-d';
	if (xtype == "" || xtype == null) {
		xtype = "textfield";
	} else if (xtype == "date3") {
		format = "y";
		xtype = "datefield";
	} else if (xtype == "date4") {
		format = "Y-m";
		xtype = "datefield";
	} else if (xtype == "date5") {
		xtype = "datefield";
	}
	if (readOnly == "" || readOnly == null) {
		readOnly = false;
	}
	if (event == "" || event == null) {
		event = Ext.emptyFn();
	}
	var field = {
		id : id,
		name : name,
		fieldLabel : label,
		xtype : xtype,
		format : format,
		anchor : '80%',
		hidden : hidden,
		readOnly : readOnly,
		listeners : event
	};
	return field;
};

/**
 * 创建一个Grid 使用: new ExtTools.createGrid(o)
 *
 * @param {}  o 对象属性如下
 * @param {}  o.id
 * @param {}  o.title(默认:列表)
 * @param {}  o.columnUrl
 * @param {}  o.storeUrl
 * @param {}  o.width
 * @param {}  o.height
 * @param {}  o.bodyStyle(默认padding:0px)
 * @param {}  o.start(默认0)
 * @param {}  o.pagesize (默认20)
 * @param {}  o.border (默认false)
 * @param {}  o.frame(默认false)
 * @param {}  o.singleSelect(默认false即多选)  o.isCheckBox
 * @param {}  o.isCheckBox(默认false即没有CheckBox)
 * @param {}  o.isHiddenColumn(默认false即不隐藏)
 * @param {}  o.hiddenSingleColumnArr(隐藏第几列[1,2,3,..])
 * @param {}  o.is
 */

ExtTools.createGrid= function (o) {
	if (id == "" || id == null) {
		Ext.Msg.alert("提示", "id 不能为空！！！<br>本信息仅用于调试 - LLJ");
		return null;
	}
       if(o.isPagingBar==null||o.isPagingBar==''){
           o.isPagingBar=false;
       }
        if(o.singleSelect==null||o.singleSelect==''){
           o.singleSelect=false;
       }
      if(o.width==null||o.width==''){
           o.width='auto';
       }
        if(o.height==null||o.height==''){
           o.height=100;
       }
       if(o.border==null||o.border==''){
           o.border=false;
       }
       if(o.frame==null||o.frame==''){
           o.frame=false;
       }
        if(o.title==null||o.title==''){
           o.title='列表';
       }
        if(o.bodyStyle==null||o.bodyStyle==''){
           o.bodyStyle='padding:0px';
       }
        if(o.isCheckBox==null||o.isCheckBox==''){
           o.isCheckBox=false;
       }
        if(o.isHiddenColumn==null||o.isHiddenColumn==''){
           o.isHiddenColumn=false;
       }
       if(o.start==null||o.start==''){
          o.start=0;
       }
       if(o.pagesize==null||o.pagesize==''){
         o.pagesize=20;
       }
       var addColumn = function () {
        this.fields = '';
        this.columns = '';
        this.addColumns = function (name, caption) {
            if (this.fields.length > 0) {
                this.fields += ',';
            }
            if (this.columns.length > 0) {
                this.columns += ',';
            }
            this.fields += '{name:"' + name + '"}';
            this.columns += '{header:"' + caption + '",dataIndex:"' + name + '", menuDisabled:true,sortable:true}';
        };
    };
    var res = ExtTools.getResponseJson(o.columnUrl,'');
    var data = new addColumn();

    for (var i = 0; i < res.length; i++) {
        var arr = new Array();
        var index = 0;
        for (var p in res[i]) {
            eval('arr[index++]=' + 'res[i].' + p);
        }
       data.addColumns(arr[1], arr[0]);
    }

    var columnArr = [];
    var rn = new Ext.grid.RowNumberer();
    columnArr.push(rn);
    if(o.isCheckBox){
	    var sm = new Ext.grid.CheckboxSelectionModel({
	        singleSelect: o.singleSelect,
	        moveEditorOnEnter: true,
	        sortable: false
	    });
	    columnArr.push(sm);
    };
    var tempColumn = eval('([' + data.columns + '])');
    for (var i = 0; i < tempColumn.length; i++) {
        columnArr.push(tempColumn[i])
    }
    var cm = new Ext.grid.ColumnModel(columnArr);
    if(o.isHiddenColumn){
        for(var i=0;i<o.hiddenSingleColumnArr.length;i++)
        {cm.setHidden(o.hiddenSingleColumnArr[i], true);}

    }
    cm.defaultSortable = true;

    var fields = eval('([' + data.fields + '])');

    var ds = new Ext.data.Store({
        id:o.id+'store',
        remoteSort: true,
        proxy: new Ext.data.HttpProxy({
            url: o.storeUrl
        }),
        reader: new Ext.data.JsonReader({
            totalProperty: "totalProperty",
            root: "root",
            fields: fields
        })
    });
    var pagingBar ='';
   if(isPagingBar){
   	  pagingBar = new Ext.PagingToolbar({
	        store: ds,
	        id:o.id+'pagingBar',
	        displayInfo: true,
	        displayMsg: '第{0} 到 {1} 条数据 共{2}条',
	        emptyMsg: "没有数据",
	        pageSize: o.pagesize
   		 });
   }
    var grid= new Ext.grid.GridPanel({
        sm: sm,
        cm: cm,
        id: o.id,
        title: o.title,
        ds: ds,
        border:o.border,
        frame:o.frame,
        bodyStyle : o.bodyStyle,
        modal:true,
        plain: true,
        layout: 'fit',
        pageSize: o.pagesize,
        columnLines : true,
        height: o.height,
        width:o.width,
        viewConfig: {
            forceFit: true
        },
        bbar:pagingBar
    });
    return grid;
};

/**
 * 创建一个Ajax 使用
 *
 * @param {}  o 对象属性如下
 * @param {}  o.url
 */
ExtTools.createAjax= function (o) {
try {
		Ext.Ajax.request({
			url :o.url ,
			method : "POST",
			success : function(result, request) {
				try{
					var ret = Ext.util.JSON.decode(result.responseText);
					//getConsole( ret.success)
					if( ret.success ){
						Ext.MessageBox.alert("提示",  ret.msg);
					}else{
						Ext.MessageBox.alert("提示",  ret.msg);
					}
				}catch(e){
					alert("server error : "+ result.responseText );
				}
			},
			failure : function(result, request) {
				Ext.Msg.alert("消息","连接超时!");
			}
		});
	} catch (e) {
		alert("请求数据的url有问题");
	}
};
/**
 * 创建一个Tree
 *
 * @param {}  o 对象属性如下
 * @param {}  o.id
 * @param {}  o.title
 * @param {}  o.rootText
 * @param {}  o.border
 * @param {}  o.width
 * @param {}  o.url
 * @param {}  o.rootVisible(true/false)
 */
ExtTools.createTree= function (o) {
             if (o.id == null || o.id == "") {
				Ext.Msg.alert("提示", "store id 不能为空！！！");
				return null;
			};
			if(o.title==null||o.title==""){
			   o.title='';
			};
			if(o.border==null||o.border==""){
			   o.border=false;
			};
			if (o.width == null || o.width == "") {
				width = 600;
			};
              var root=new Ext.tree.AsyncTreeNode({
                    id:'0',
                    text:o.rootText,
                    expanded :true
                });
                var data=new Ext.tree.TreeLoader({url:o.url});
                var tree=new Ext.tree.TreePanel({
                    id:o.id,
                    title:o.title,
                    rootVisible:o.rootVisible,
                    root:root,
                    loader:data,
                    border:o.border,
                    width:o.width
                });
                data.on('beforeload',function(treeLoader,node){
                    this.baseParams.id=(node == undefined ? "0" : node.id);
                },data);
    return tree;

};


/**
 * 获得正则表达式
 *
 * @param {}  o 类型
 */
ExtTools.getRegex= function (o) {
	var regexArr='';
	switch (o) {
	    case "alpha":
	        regexArr=[/^[a-zA-Z]+$/,'只能是字母'];
	        break;
	    case "num":
	       regexArr=[/^-?\d+$/,'只能输入数字'];
	        break;
	    case "float":
	       regexArr=[ /^-?\d+\.\d+$/,'只能输入小数'];
	       break;
	    default:
	      regexArr=[ /^[\u4E00-\u9FA5]+$/,'输入中文'];
	    };
    return regexArr;
};

/**
 * 得到iframe所在页面
 */
ExtTools.getIFramePage = function() {
    return window.parent.parent;
};
/**
 * 得到iframe中的页面
 *
 * @param {} iframeId 框架id
 */
ExtTools.getIFrameInnerPage = function(iframeId) {
    return Ext.get(iframeId).dom.contentWindow;
};
/**
 * 可见尺寸{ width,height}
 */
ExtTools.viewSize={
	width:	Ext.getBody().getViewSize().width,
	height: Ext.getBody().getViewSize().height

};
/**
 * @param [0] boolean 是否启用debug调试
 * @param [1] object
 * @param [2] object
 * ExtTools.console(true,agr1,agr2)
 * since July 20 2011
 */
ExtTools.console=function(){
	if(arguments[0]){
		switch (arguments.length) {
	    case 3:
	    	if(!Ext.isIE){
	    		console.log(arguments[1],arguments[2]);
	    	};
	        break;
	    default:
	    	if(!Ext.isIE){
	    		console.log(arguments[1]);
	    	};
		};
	};
}
/**
 * ===================Ext中常用方法调用===============
 */
/**
 * 得到对象
 * @param id
 * @return
 */
function get(id){
	return Ext.get(id);
};
/**
 * 得到页面元素
 * @param id
 * @return
 */
function getEl(id){
	return Ext.get(id).dom;
};
/**
 * 得到容器
 * @param id
 * @return
 */
function getCmp(id){
	return Ext.getCmp(id);
};
/**
 * 信息提示
 * @param c
 * @return
 */
function getMsg(){
	switch (arguments.length) {
	    case 2:
	    	Ext.MessageBox.alert(arguments[0],arguments[1]);
	        break;
	    default:
	    	Ext.MessageBox.alert('提示', arguments[0]);
	};

};
/**
 * 传统方式得到Element
 * @param id
 * @return
 */
function docEl(id){
	return document.getElementById(id);
};
/**
 * bug调试
 *
 *
 */
function getConsole(){
	switch (arguments.length) {
	    case 2:
	    	if(!Ext.isIE){
	    		console.log(arguments[0],arguments[1]);
	    	};
	        break;
	    default:
	    	if(!Ext.isIE){
	    		console.log(arguments[0]);
	    	};
	};
};

