/*命名空间*/
Ext.namespace("gridview.app.filesystem.webapp.hamonitor.monitor");
/**
 * ================================================拓扑展示框架Panel=========================
 *
 * @param {}config
 * @author LLJ
 * @since February 23 2012
 *
 */
gridview.app.filesystem.webapp.hamonitor.monitor.framePanel = function (config) {
	gridview.app.filesystem.webapp.hamonitor.monitor.framePanel.superclass.constructor.call(this, config);
    this.initLayout();
};

Ext.extend(
		gridview.app.filesystem.webapp.hamonitor.monitor.framePanel, Ext.util.Observable, {
    /**
     * 框架左侧
     */
    westPanel: '',
    /**
     * 框架中部
     */
    centerPanel: '',
    /**
     * 启动定时更新的标志
     */
    enableTimer: true,
    /**
     * 定时更新的时间间隔
     */
    interval: 300,
    /**
     * 最小更新时间间隔
     */
    intervalMin: 20,
    /**
     * 最大更新时间间隔
     */
    intervalMax: 300,
    treeAction: networktopology_path + "imms/designerTree.action",
    selectCanvasAction: networktopology_path + "parastormonitor/queryCanvas.action",
    infoPanelId: 'displayTopologyInfoPanelId',
    /**
     * 布局初始化
     */
    initLayout: function () {
        informationcollector_displayTopology_frameme = this;
        this.westPanel = new Ext.Panel({
            id: 'netTopologyWestPanelId',
            margins: '3 0 0 0',
            layout: "fit",
            border: true,
            split: true,
            width: 200,
            region: "west",
            collapseMode: 'mini',
            collapsible: true,
            items: [this.initWestPanel()]
        });
        this.centerPanelItem = new gridview.app.filesystem.webapp.hamonitor.monitor.canvasPanel({
            applyTo: 'canvasDivId',
            bodyStyle: 'padding:0px;background:transparent',
            autoScroll: true
        });
        var tbar = [];
        this.setCenterPanelTbar(tbar)
        this.centerPanel = new Ext.Panel({
            id: 'netTopologyCenterPanelId',
            margins: '3 0 0 0',
            layout: "fit",
            //border: true,
            region: "center",
            tbar: tbar,
            autoScroll: true,
            //['->',manulRefreshAction,setRefreshIntervalAction,timerCheckBox],
            items: [this.centerPanelItem]
        });

        // 初始化主页面
        var mainPanel = new Ext.Panel({
            // applyTo : "imms_realdata_main_div",
            id: "netTopologyLayoutId",
            layout: "border",
            width: Ext.getBody().getViewSize().width,
            height: Ext.getBody().getViewSize().height,
            autoScroll: true,
            //items: [this.westPanel, this.centerPanel]
            items: [this.centerPanel]
        });
        // 获取tab页
        var tabPanel = appLayout.getTab(networktopology_tab_ID);
        // 将本页加入容器
        appLayout.addTabPanel(networktopology_tab_ID, mainPanel);
        tabPanel.addListener("activate", function () {
            informationcollector_displayTopology_frameme.framePanelActivate()
        }, this);
        tabPanel.addListener("deactivate", function () {
            informationcollector_displayTopology_frameme.framePanelDeactivate()
        }, this);
        tabPanel.addListener('destroy', function () {
            informationcollector_displayTopology_frameme.framePanelDestroy()
        }, this);
        this.initTimeTask();
    },
    listeners: {},
    // 左边资源树
    initWestPanel: function () {
        var panel = new Ext.Panel({
            id: 'netTopologyWestPanelId',
            layout: 'accordion',
            width: 200,
            height: 200,
            layoutConfig: {
                animate: true

            },
            defaults: {
                bodyStyle: 'padding:0px'
            },
            items: [{
                title: '设备',
                items: [{
                    html: '<div id="netTopologyWestDivId"></div>',
                    bodyStyle: 'padding:0px'
                }]
            }],
            listeners: {
                afterrender: function () {


                }
            },
            initData: function () {

            }

        }

        );
        return panel;

    },
    /**
     * 隐藏信息提示框
     */
    hideInfoPanel: function () {
        var infop = Ext.getCmp(this.infoPanelId);
        if (infop) {
            infop.hide() //destroy();
        }
    },
    /**
     * 初始化定时任务
     */
    initTimeTask: function () {
        var intervaltemp = this.interval;
        /**
         * 定义用于定时执行的任务
         */
        this.task = {
            run: function () {
                informationcollector_displayTopology_frameme.centerPanelItem.refreshData();
            },
            interval: intervaltemp * 1000,
            scope: this
        };

        /**
         * 用于定时任务的操作句柄
         */
        this.runner = new Ext.util.TaskRunner();
        if (this.enableTimer) {
            this.runner.start(this.task);
        }
    },
    /**
     * 处理监控页面销毁事件
     */
    framePanelDestroy: function () {
        this.hideInfoPanel();
        /**
         * 停止定时任务
         */
        if (this.enableTimer) {

            if (this.runner != null) {
                this.runner.stop(this.task);
            }
        }
    },

    /**
     * 处理监控页面激活事件
     */
    framePanelActivate: function () {

        /**
         * 如果允许定时任务标志位为true，则启动定时任务
         */
        if (this.runner != null) {
            if (this.enableTimer) {
                this.runner.start(this.task);
            } else {
                informationcollector_displayTopology_frameme.centerPanelItem.refreshData();
            }
        }
    },
    /**
     * 处理监控页面非激活事件
     */
    framePanelDeactivate: function () {
        this.hideInfoPanel();
        /**
         * 停止定时任务
         */
        if (this.enableTimer) {
            if (this.runner != null) {
                this.runner.stop(this.task);
            }
        }
    },

    closeHandler: function () {
        if (this.runner != null) {
            this.runner.stop(this.task);
        }
    },
    /**
     * 设置刷新的时间间隔
     */
    setTimerInterval: function () {
        Ext.MessageBox.prompt(gvI18n('ic_set_refresh_interval'), gvI18n('ic_refresh_interval_range', this.intervalMin, this.intervalMax), function (btn, text) {
            if (btn == 'ok') {
                var value = parseInt(text);
                if (isNaN(value)) {
                    Ext.MessageBox.alert(gvI18n('error'), gvI18n('ic_input_is_not_number'));
                } else {
                    if (value < this.intervalMin || value > this.intervalMax) {
                        Ext.MessageBox.alert(gvI18n('error'), gvI18n('ic_number_limit', this.intervalMin, this.intervalMax));
                    } else {
                        this.interval = value;
                        this.task["interval"] = this.interval * 1000;
                        if (this.runner != null) {
                            this.runner.stop(this.task);
                            this.runner.start(this.task);
                        }
                    }
                }
            }
        }, this, false, this.interval);
    },

    /**
     * handle--当启动定时按键的状态被改变时触发此函数
     */
    onTimerStatusChanged: function (item, checked) {
        this.enableTimer = checked;
        if (this.runner != null) {
            if (this.enableTimer) {
                this.runner.start(this.task);
            } else {
                this.runner.stop(this.task);
            }
        }
    },
    /**
     * 创建下拉框
     */
    createCombo: function (o) {
        if (o.emptyText == undefined || o.emptyText == null || o.emptyText == "") {
            o.emptyText = "请输选择"; //"请输入";
        }
        var store = new Ext.data.Store({
            id: o.id + "StoreId",
            reader: new Ext.data.JsonReader({
                root: 'items',
                fields: [{
                    name: 'ID'
                },
                {
                    name: 'NAME'
                }]
            }),
            proxy: new Ext.data.HttpProxy({
                url: o.url
            }),
            autoLoad: false,
            remoteSort: true

        });
        store.load();
        var field = new Ext.form.ComboBox({
            xtype: 'combo',
            id: o.id,
            store: store,
            valueField: 'ID',
            displayField: 'NAME',
            mode: 'local',
            editable: false,
            //hiddenName: o.param,
            triggerAction: 'all',
            //fieldLabel: o.label,
            emptyText: o.emptyText,
            //labelWidth: 70,
            width: 120,
            listeners: {
                'select': function (thiscom, thisrecord, thisindex) {
                    informationcollector_displayTopology_frameme.centerPanelItem.currentCanvasId = thisrecord.get("ID");
                    informationcollector_displayTopology_frameme.centerPanelItem.refreshData();
                }
            }
        });
        return field;
    },
    setCenterPanelTbar: function (tbar) {
//        var selectComb = this.createCombo({
//            id: 'networkTopologyFramePanelTbarCombId',
//            //label: '选择分区',
//            url: this.selectCanvasAction
//        })
//        tbar.push({
//            xtype: 'label',
//            text: '选择分区',
//            width: 70
//        })
//        tbar.push(selectComb)
        tbar.push("->")

        /**
         * 手动刷新按钮
         */
        var manulRefreshAction = new Ext.Action({
            text: gvI18n('ic_manual_refresh'),
            handler: function () {
                informationcollector_displayTopology_frameme.centerPanelItem.refreshData()
            },
            tooltip: gvI18n('ic_manual_refresh'),
            iconCls: "menu_refresh",
            scope: this
        });


        tbar.push(manulRefreshAction)
        /**
         * 设置刷新时间间隔按钮
         */
        var setRefreshIntervalAction = new Ext.Action({
            text: gvI18n('ic_set_refresh_interval'),
            handler: this.setTimerInterval,
            tooltip: gvI18n('ic_set_refresh_interval'),
            iconCls: "menu_refresh_settings",
            scope: this
        });
        tbar.push(setRefreshIntervalAction)
        /**
         * 是否自动更新复选框
         */
//        var timerCheckBox = new Ext.form.Checkbox({
//            boxLabel: gvI18n('ic_autoupdate'),
//            checked: this.enableTimer
//        });
//        /**
//         * 添加自动更新复选框check事件处理
//         */
//        timerCheckBox.on('check', this.onTimerStatusChanged, this);
//        tbar.push(timerCheckBox)

    }


});
/**
 * 初始化函数
 */

function initDisplayTopologyFramePanel() {
    new gridview.app.filesystem.webapp.hamonitor.monitor.framePanel();
}
Ext.onReady(initDisplayTopologyFramePanel);