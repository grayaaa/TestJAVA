/**
 * 拓扑 展示Panel
 *
 * @author LLJ
 * @since February 24 2012
 * @vision 1.0
 *
 * @vision 1.2
 * @update 添加端口号
 */
/* 命名空间 */
Ext.namespace("gridview.app.filesystem.webapp.hamonitor.monitor");
/* 拓扑 画布Panel */
gridview.app.filesystem.webapp.hamonitor.monitor.canvasPanel = Ext.extend(
		Ext.Panel, {
			id : 'canvasPanelId',
			prefix : 'displayTopology_',
			width : Ext.getBody().getViewSize().width,
			height : Ext.getBody().getViewSize().height,
			border : false,
			autoScroll : true,
			/**
			 * DD对象数组
			 */
			ddArr : [],
			/**
			 * DD对象数组,利用Id下标直接获取DD对象
			 */
			ddArrObj : {},
			/**
			 * 节点ID数组
			 */
			nodeIdsArr : [],
			/**
			 * 节点关系数组对象,利用Id下标直接获取节点关系
			 */
			nodeRelationArrObj : {},
			/**
			 * haoParas节点对
			 */
			haoParas : {},
			/**
			 * 所有的oPara对
			 */
			allHAoParaPairs : {},
			/**
			 * 所有oStor
			 */
			allHAoStors : {},

			/**
			 * 存储系统监控数据
			 */
			storSystemData : {},
			/**
			 * 主画布Id
			 */
			mainCanvasId : 'displayTopology_mainGraph',
			/**
			 * 副画布Id
			 */
			subCanvasId : 'displayTopology_subGraph',
			/**
			 * 当前画布Id
			 */
			currentCanvasId : '',
			/**
			 * 主画布
			 */
			mainCanvas : '',
			/**
			 * 主拖动区域Id
			 */
			mainDragZoneId : 'canvasDivId',
			/**
			 * 提示信息框PanelId
			 */
			infoPanelId : 'displayTopologyInfoPanelId',
			/**
			 * 配置
			 */
			cf : '',
			/**
			 * oPara数据Action
			 */
			baseDataUrl : "/ha_monitor/parastormonitor/getoParaData.action",
			/**
			 * oStor数据Action
			 */
			ostorDataUrl : "/ha_monitor/parastormonitor/getoStorData.action",

			/**
			 *
			 * 存储系统监控数据Action
			 */
			storSystemDataUrl : "/ha_monitor/parastormonitor/getStorSystemData.action",

			imageUrl : "/ha_monitor/resources/images/",

			/**
			 * 是否已渲染
			 */
			isRender : false,
			/**
			 * 坐标精确度
			 */
			xyAccurate : 1000,
			/**
			 * 是否在拖动
			 */
			isOnDrag : false,
			/**
			 * 结束点字段名称
			 */
			endNodeFieldName : 'en',
			/**
			 * 端口号字段名称
			 */
			portFieldName : 'port',
			/**
			 * 端口状态
			 */
			portStatusFieldName : 'status',
			/**
			 * 内容展示实际高度
			 */
			dashboardHeight : '',
			/**
			 * 内容展示实际宽度
			 */
			dashboardWidth : '',
			maxZindex : 0,
			/**
			 * oPara与oStor之间距离
			 */
			oParaoStorHeight : 150,
			/**
			 * 网线长度
			 */
			netLength : 950,

			/**
			 * 服务状态列表
			 *
			 * @type
			 */
			serviceState : ["service_broken", "service_close",
					"service_closing", "service_ok", "service_ready",
					"service_repair", "service_shutdown", "service_unknown",
					"service_unconfig"],

			defaultServiceState : 'service_unknown',
			/**
			 * 初始化
			 */
			initComponent : function() {

				informationcollector_displayTopology_canvasPanelme = this;
				this.cf = informationcollectorNetworktopologyCanvasPanelConfig;
				// this.items=[{html:'<div
				// id="canvasDivId"></div>',bodyStyle:'padding:0px;'}];
				this.createMainCanvas();
				gridview.app.filesystem.webapp.hamonitor.monitor.canvasPanel.superclass.initComponent
						.call(this);
			},
			listeners : {
				afterrender : function() {
					informationcollector_displayTopology_canvasPanelme.isRender = true;
					var appTabPanel = appLayout.getTab(networktopology_tab_ID);
					// 内容展示实际高度
					informationcollector_displayTopology_canvasPanelme.dashboardHeight = appTabPanel
							.getInnerHeight()
							- 50;
					informationcollector_displayTopology_canvasPanelme.dashboardWidth = Ext
							.getBody().getViewSize().width;
				}
			},

			/**
			 * 根据key取得array中value值
			 */
			getNodeFromArray : function(elements, key) {

				try {
					for (var i = 0; i < elements.length; i++) {
						if (elements[i] != null && elements[i].key == key) {
							return elements[i].value;
						}
					}
				} catch (e) {
					return null;
				}

			},
			/**
			 * 跟据key删除array中元素
			 */
			deleteNodeFromArray : function(elements, key) {
				try {
					for (var i = 0; i < elements.length; i++) {
						if (elements[i] != null && elements[i].key == key) {
							delete elements[i];
						}
					}
				} catch (e) {

				}
			},
			/**
			 * 初始化ostor节点
			 */
			initoStorNode : function() {

				var locationMap = new Array();
				var yArray = new Array();
				for (var ostorId in this.allHAoStors) {
					var ostorNode = this.allHAoStors[ostorId];

					var ostorNodeDisplayXY = this.getDisplayXY(ostorNode);
					ostorNode.x = ostorNodeDisplayXY.x;
					ostorNode.y = ostorNodeDisplayXY.y;

					var nodemin = this.getNodeFromArray(locationMap,
							ostorNode.y + "min");
					if (!(nodemin != null && ostorNode.x > nodemin.x)) {
						this.deleteNodeFromArray(locationMap, ostorNode.y
										+ "min");
						locationMap.push({
									key : ostorNode.y + "min",
									value : ostorNode
								});

					}
					var nodemax = this.getNodeFromArray(locationMap,
							ostorNode.y + "max");
					if (!(nodemax != null && ostorNode.x < nodemax.x)) {
						this.deleteNodeFromArray(locationMap, ostorNode.y
										+ "max");
						locationMap.push({
									key : ostorNode.y + "max",
									value : ostorNode
								});

					}
					var mark = false;
					for (var i = 0; i < yArray.length; i++) {
						if (yArray[i] == ostorNode.y) {
							mark = true;
						}
					}
					if (!mark) {
						yArray.push(ostorNode.y);
					}

					this
							.createoStorNode(ostorNodeDisplayXY, ostorNode,
									ostorId);

				}

				// 计算网线的位置

				for (var i = 0; i < yArray.length; i++) {
					var omin = this.getNodeFromArray(locationMap, yArray[i]
									+ "min");

					this.addNetImageToCanvas(omin.y, omin.x);
					// this.addNetImageToCanvas(omin.y - 60, omax.x, rwidth,
					// false);
				}

			},

			/**
			 * 节点初始化<br>
			 * 初始化时将nodeRelationArrObj中的XY坐标由原来的百分比转成真是的像素坐标<br>
			 */
			initNode : function(haoParaPair, haId) {

				var leftoPara = haoParaPair["left"];
				var rightoPara = haoParaPair["right"];

				var oParaLeftDisplayXY = this.getDisplayXY(leftoPara);
				leftoPara.x = oParaLeftDisplayXY.x;
				leftoPara.y = oParaLeftDisplayXY.y;
				var oParaRightDisplayXY = this.getDisplayXY(rightoPara);
				rightoPara.x = oParaRightDisplayXY.x;
				rightoPara.y = oParaRightDisplayXY.y;
				this.createHAoParasNode(oParaLeftDisplayXY, leftoPara,
						oParaRightDisplayXY, rightoPara, haId);

				new Ext.dd.DDTarget(this.mainDragZoneId, 'DDGroup');

			},
			initCanvasSize : function() {
				var mainCanvas = document.getElementById(this.mainCanvasId);
				mainCanvas.width = Ext.getBody().getViewSize().width - 40;
				mainCanvas.height = this.dashboardHeight;
				document.getElementById(this.mainDragZoneId).style.width = Ext
						.getBody().getViewSize().width
						- 20 + 'px';
				document.getElementById(this.mainDragZoneId).style.height = this.dashboardHeight
						+ 'px';
				document.getElementById(this.mainCanvasId).className = 'mainGraph';
			},

			/**
			 * 生成存储系统全局监控信息
			 */
			createStorSystemMonitorInfo : function() {

				var storSystemDiv = document.createElement('fieldset');
				storSystemDiv.id = "storSystemDiv";
				storSystemDiv.style.position = "absolute";
				storSystemDiv.style.border = "medium double #B5B5B5";
				storSystemDiv.style.top = this.resetImageHeight(30) + "px";
				storSystemDiv.style.left = this.resetImageWidth(10) + "px";
				storSystemDiv.style.width = this.resetImageWidth(300) + "px";
				storSystemDiv.style.height = this.resetImageHeight(280) + "px";
				storSystemDiv.style.zIndex = 200;

				var lengedTitle = document.createElement('legend');
				lengedTitle.style.textAlign = "center";
				lengedTitle.innerHTML = "存储系统信息概览";

				storSystemDiv.appendChild(lengedTitle);

				// var storSystemDiv = document.createElement('div');
				// storSystemDiv.id = "storSystemDiv";
				// storSystemDiv.style.position = "absolute";
				// storSystemDiv.style.border = "medium double #B5B5B5";
				// storSystemDiv.style.top = this.resetImageHeight(30) + "px";
				// storSystemDiv.style.left = this.resetImageWidth(10) + "px";
				// storSystemDiv.style.width = this.resetImageWidth(300) + "px";
				// storSystemDiv.style.height = this.resetImageHeight(260) +
				// "px";
				// storSystemDiv.style.zIndex = 200;

				// 存储系统状态
				var OfsPfsStateDiv = document.createElement('div');

				OfsPfsStateDiv.id = "OfsPfsStateDiv";
				OfsPfsStateDiv.style.position = "absolute";
				OfsPfsStateDiv.style.float = "left";
				OfsPfsStateDiv.style.textAlign = "right";
				OfsPfsStateDiv.style.top = this.resetImageHeight(30) + "px";
				OfsPfsStateDiv.style.left = this.resetImageWidth(10) + "px";
				OfsPfsStateDiv.style.width = this.resetImageWidth(100) + "px";
				OfsPfsStateDiv.style.height = this.resetImageHeight(20) + "px";
				OfsPfsStateDiv.innerHTML = gvI18n("系统状态");
				storSystemDiv.appendChild(OfsPfsStateDiv);

				// 存储系统状态
				var OfsPfsStateTitleDiv = document.createElement('div');

				OfsPfsStateTitleDiv.id = "OfsPfsStateDiv";
				OfsPfsStateTitleDiv.style.position = "absolute";
				OfsPfsStateTitleDiv.style.float = "right";
				OfsPfsStateTitleDiv.style.textAlign = "left";
				OfsPfsStateTitleDiv.style.top = this.resetImageHeight(30)
						+ "px";
				OfsPfsStateTitleDiv.style.left = this.resetImageWidth(110)
						+ "px";
				OfsPfsStateTitleDiv.style.width = this.resetImageWidth(170)
						+ "px";
				OfsPfsStateTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var statetext = this.storSystemData.OfsPfsState;
				if (statetext == undefined) {
					statetext = '';
				}
				OfsPfsStateTitleDiv.innerHTML = "：" + statetext;
				storSystemDiv.appendChild(OfsPfsStateTitleDiv);

				// 存储系统总容量
				var capacityDiv = document.createElement('div');
				capacityDiv.id = "capacityDiv";
				capacityDiv.style.position = "absolute";
				capacityDiv.style.float = "left";
				capacityDiv.style.textAlign = "right";
				capacityDiv.style.top = this.resetImageHeight(60) + "px";
				capacityDiv.style.left = this.resetImageWidth(10) + "px";
				capacityDiv.style.width = this.resetImageWidth(100) + "px";
				capacityDiv.style.height = this.resetImageHeight(20) + "px";
				capacityDiv.innerHTML = "系统总容量";
				storSystemDiv.appendChild(capacityDiv);

				// 存储系统总容量
				var capacityTitleDiv = document.createElement('div');
				capacityTitleDiv.id = "capacityTitleDiv";
				capacityTitleDiv.style.position = "absolute";
				capacityTitleDiv.style.float = "right";
				capacityTitleDiv.style.textAlign = "left";
				capacityTitleDiv.style.top = this.resetImageHeight(60) + "px";
				capacityTitleDiv.style.left = this.resetImageWidth(110) + "px";
				capacityTitleDiv.style.width = this.resetImageWidth(170) + "px";
				capacityTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var capacitytext = this.storSystemData.OfsPfs_capacity;
				if (capacitytext == undefined) {
					capacitytext = '';
				} else {
					capacitytext = capacitytext + "GB";
				}
				capacityTitleDiv.innerHTML = "：" + capacitytext;
				storSystemDiv.appendChild(capacityTitleDiv);

				// 剩余容量
				var freeSpaceDiv = document.createElement('div');
				freeSpaceDiv.id = "freeSpaceDiv";
				freeSpaceDiv.style.position = "absolute";
				freeSpaceDiv.style.float = "left";
				freeSpaceDiv.style.textAlign = "right";
				freeSpaceDiv.style.top = this.resetImageHeight(90) + "px";
				freeSpaceDiv.style.left = this.resetImageWidth(10) + "px";
				freeSpaceDiv.style.width = this.resetImageWidth(100) + "px";
				freeSpaceDiv.style.height = this.resetImageHeight(20) + "px";
				freeSpaceDiv.innerHTML = "剩余容量";
				storSystemDiv.appendChild(freeSpaceDiv);

				// 剩余容量
				var freeSpaceTitleDiv = document.createElement('div');
				freeSpaceTitleDiv.id = "freeSpaceTitleDiv";
				freeSpaceTitleDiv.style.position = "absolute";
				freeSpaceTitleDiv.style.float = "right";
				freeSpaceTitleDiv.style.textAlign = "left";
				freeSpaceTitleDiv.style.top = this.resetImageHeight(90) + "px";
				freeSpaceTitleDiv.style.left = this.resetImageWidth(110) + "px";
				freeSpaceTitleDiv.style.width = this.resetImageWidth(170)
						+ "px";
				freeSpaceTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var freespacetext = this.storSystemData.OfsPfs_free_space;
				if (freespacetext == undefined) {
					freespacetext = '';
				} else {
					freespacetext = freespacetext + "GB";
				}
				freeSpaceTitleDiv.innerHTML = "：" + freespacetext;
				storSystemDiv.appendChild(freeSpaceTitleDiv);

				// 已用容量
				var userdSpaceDiv = document.createElement('div');
				userdSpaceDiv.id = "userdSpaceDiv";
				userdSpaceDiv.style.position = "absolute";
				userdSpaceDiv.style.float = "left";
				userdSpaceDiv.style.textAlign = "right";
				userdSpaceDiv.style.top = this.resetImageHeight(120) + "px";
				userdSpaceDiv.style.left = this.resetImageWidth(10) + "px";
				userdSpaceDiv.style.width = this.resetImageWidth(100) + "px";
				userdSpaceDiv.style.height = this.resetImageHeight(20) + "px";
				userdSpaceDiv.innerHTML = "已用容量";
				storSystemDiv.appendChild(userdSpaceDiv);
				// 已用容量
				var userdSpaceTitleDiv = document.createElement('div');
				userdSpaceTitleDiv.id = "userdSpaceDiv";
				userdSpaceTitleDiv.style.position = "absolute";
				userdSpaceTitleDiv.style.float = "right";
				userdSpaceTitleDiv.style.textAlign = "left";
				userdSpaceTitleDiv.style.top = this.resetImageHeight(120)
						+ "px";
				userdSpaceTitleDiv.style.left = this.resetImageWidth(110)
						+ "px";
				userdSpaceTitleDiv.style.width = this.resetImageWidth(170)
						+ "px";
				userdSpaceTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var usedspacetext = this.storSystemData.OfsPfs_used_space;
				if (usedspacetext == undefined) {
					usedspacetext = '';
				} else {
					usedspacetext = usedspacetext + "GB";
				}
				userdSpaceTitleDiv.innerHTML = "：" + usedspacetext;
				storSystemDiv.appendChild(userdSpaceTitleDiv);

				// 存储使用率
				var capcityRateDiv = document.createElement('div');
				capcityRateDiv.id = "capcityRateDiv";
				capcityRateDiv.style.position = "absolute";
				capcityRateDiv.style.float = "left";
				capcityRateDiv.style.textAlign = "right";
				capcityRateDiv.style.top = this.resetImageHeight(150) + "px";
				capcityRateDiv.style.left = this.resetImageWidth(10) + "px";
				capcityRateDiv.style.width = this.resetImageWidth(100) + "px";
				capcityRateDiv.style.height = this.resetImageHeight(20) + "px";
				capcityRateDiv.innerHTML = "存储使用率";
				storSystemDiv.appendChild(capcityRateDiv);
				// 存储使用率
				var capcityRateTitleDiv = document.createElement('div');
				capcityRateTitleDiv.id = "capcityRateTitleDiv";
				capcityRateTitleDiv.style.position = "absolute";
				capcityRateTitleDiv.style.float = "right";
				capcityRateTitleDiv.style.textAlign = "left";
				capcityRateTitleDiv.style.top = this.resetImageHeight(150)
						+ "px";
				capcityRateTitleDiv.style.left = this.resetImageWidth(110)
						+ "px";
				capcityRateTitleDiv.style.width = this.resetImageWidth(170)
						+ "px";
				capcityRateTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var usedratetext = this.storSystemData.OfsPfs_used_rate;
				if (usedratetext == undefined) {
					usedratetext = '';
				} else {
					usedratetext = usedratetext + "%";
				}
				capcityRateTitleDiv.innerHTML = "：" + usedratetext;
				storSystemDiv.appendChild(capcityRateTitleDiv);

				// 读速率
				var readRateDiv = document.createElement('div');
				readRateDiv.id = "readRateDiv";
				readRateDiv.style.position = "absolute";
				readRateDiv.style.float = "left";
				readRateDiv.style.textAlign = "right";
				readRateDiv.style.top = this.resetImageHeight(180) + "px";
				readRateDiv.style.left = this.resetImageWidth(10) + "px";
				readRateDiv.style.width = this.resetImageWidth(100) + "px";
				readRateDiv.style.height = this.resetImageHeight(20) + "px";
				readRateDiv.innerHTML = "聚合读速率";
				storSystemDiv.appendChild(readRateDiv);
				// 读速率
				var readRateTitleDiv = document.createElement('div');
				readRateTitleDiv.id = "readRateTitleDiv";
				readRateTitleDiv.style.position = "absolute";
				readRateTitleDiv.style.float = "right";
				readRateTitleDiv.style.textAlign = "left";
				readRateTitleDiv.style.top = this.resetImageHeight(180) + "px";
				readRateTitleDiv.style.left = this.resetImageWidth(110) + "px";
				readRateTitleDiv.style.width = this.resetImageWidth(120) + "px";
				readRateTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var readratetext = this.storSystemData.OfsPfs_ds_read_rate;
				if (readratetext == undefined) {
					readratetext = '';
				} else {
					readratetext = readratetext + "MB/s";
				}
				readRateTitleDiv.innerHTML = "：" + readratetext;
				storSystemDiv.appendChild(readRateTitleDiv);

				// 写速率
				var writeRateDiv = document.createElement('div');
				writeRateDiv.id = "writeRateDiv";
				writeRateDiv.style.position = "absolute";
				writeRateDiv.style.float = "left";
				writeRateDiv.style.textAlign = "right";
				writeRateDiv.style.top = this.resetImageHeight(210) + "px";
				writeRateDiv.style.left = this.resetImageWidth(10) + "px";
				writeRateDiv.style.width = this.resetImageWidth(100) + "px";
				writeRateDiv.style.height = this.resetImageHeight(20) + "px";
				writeRateDiv.innerHTML = "聚合写速率";
				storSystemDiv.appendChild(writeRateDiv);
				// 写速率
				var writeRateTitleDiv = document.createElement('div');
				writeRateTitleDiv.id = "writeRateTitleDiv";
				writeRateTitleDiv.style.position = "absolute";
				writeRateTitleDiv.style.float = "right";
				writeRateTitleDiv.style.textAlign = "left";
				writeRateTitleDiv.style.top = this.resetImageHeight(210) + "px";
				writeRateTitleDiv.style.left = this.resetImageWidth(110) + "px";
				writeRateTitleDiv.style.width = this.resetImageWidth(170)
						+ "px";
				writeRateTitleDiv.style.height = this.resetImageHeight(20)
						+ "px";

				var writeratetext = this.storSystemData.OfsPfs_ds_write_rate;
				if (writeratetext == undefined) {
					writeratetext = '';
				} else {
					writeratetext = writeratetext + "MB/s";
				}
				writeRateTitleDiv.innerHTML = "：" + writeratetext;
				storSystemDiv.appendChild(writeRateTitleDiv);

				// 元数据IOPS
				var iopsDiv = document.createElement('div');
				iopsDiv.id = "iopsDiv";
				iopsDiv.style.position = "absolute";
				iopsDiv.style.float = "left";
				iopsDiv.style.textAlign = "right";
				iopsDiv.style.top = this.resetImageHeight(240) + "px";
				iopsDiv.style.left = this.resetImageWidth(10) + "px";
				iopsDiv.style.width = this.resetImageWidth(100) + "px";
				iopsDiv.style.height = this.resetImageHeight(20) + "px";
				iopsDiv.innerHTML = "IOPS";
				storSystemDiv.appendChild(iopsDiv);
				// 元数据IOPS
				var iopsTitleDiv = document.createElement('div');
				iopsTitleDiv.id = "iopsTitleDiv";
				iopsTitleDiv.style.position = "absolute";
				iopsTitleDiv.style.float = "right";
				iopsTitleDiv.style.textAlign = "left";
				iopsTitleDiv.style.top = this.resetImageHeight(240) + "px";
				iopsTitleDiv.style.left = this.resetImageWidth(110) + "px";
				iopsTitleDiv.style.width = this.resetImageWidth(170) + "px";
				iopsTitleDiv.style.height = this.resetImageHeight(20) + "px";

				var iopstext = this.storSystemData.OfsPfs_ds_reqs_rate;
				if (iopstext == undefined) {
					iopstext = '';
				} else {
					iopstext = iopstext + "req/s";
				}
				iopsTitleDiv.innerHTML = "：" + iopstext;
				storSystemDiv.appendChild(iopsTitleDiv);

				Ext.get(this.mainDragZoneId).dom.appendChild(storSystemDiv);
			},

			/**
			 * 生成图例区
			 */
			createLegend : function() {
				var legendDiv = document.createElement('fieldset');
				legendDiv.id = "legenddiv";
				legendDiv.style.position = "absolute";
				legendDiv.style.border = "medium double #B5B5B5";
				legendDiv.style.top = this.resetImageHeight(30) + "px";
				legendDiv.style.left = this.resetImageWidth(1680) + "px";
				legendDiv.style.width = this.resetImageWidth(200) + "px";
				legendDiv.style.height = this.resetImageHeight(280) + "px";
				legendDiv.style.zIndex = 200;
				var lengedTitle = document.createElement('legend');
				lengedTitle.style.textAlign = "center";
				lengedTitle.innerHTML = "图例区";
				legendDiv.appendChild(lengedTitle);

				// service_ok
				var okImg = document.createElement('div');
				okImg.id = "ok_Img";
				okImg.style.float = "left";
				okImg.style.position = "absolute";
				okImg.style.backgroundColor = "#4cc25f";
				okImg.style.top = this.resetImageHeight(30) + "px";
				okImg.style.left = this.resetImageWidth(10) + "px";
				okImg.style.width = this.resetImageWidth(20) + "px";
				okImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(okImg);

				var okTitle = document.createElement('div');
				okTitle.id = "ok_Title";
				okTitle.style.float = "right";
				okTitle.style.position = "absolute";
				okTitle.style.top = this.resetImageHeight(30) + "px";
				okTitle.style.left = this.resetImageWidth(35) + "px";
				okTitle.style.width = this.resetImageWidth(65) + "px";
				okTitle.style.height = this.resetImageHeight(20) + "px";
				okTitle.innerHTML = "service_ok";
				legendDiv.appendChild(okTitle);

				// service_close
				var closeImg = document.createElement('div');
				closeImg.id = "close_Img";
				closeImg.style.float = "left";
				closeImg.style.position = "absolute";
				closeImg.style.backgroundColor = "#FFA500";
				closeImg.style.top = this.resetImageHeight(60) + "px";
				closeImg.style.left = this.resetImageWidth(10) + "px";
				closeImg.style.width = this.resetImageWidth(20) + "px";
				closeImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(closeImg);

				var closeTitle = document.createElement('div');
				closeTitle.id = "close_Title";
				closeTitle.style.float = "right";
				closeTitle.style.position = "absolute";
				closeTitle.style.top = this.resetImageHeight(60) + "px";
				closeTitle.style.left = this.resetImageWidth(35) + "px";
				closeTitle.style.width = this.resetImageWidth(65) + "px";
				closeTitle.style.height = this.resetImageHeight(20) + "px";
				closeTitle.innerHTML = "service_close";
				legendDiv.appendChild(closeTitle);

				// service_unknown
				var unknownImg = document.createElement('div');
				unknownImg.id = "unknown_Img";
				unknownImg.style.float = "left";
				unknownImg.style.position = "absolute";
				unknownImg.style.backgroundColor = "#B5B5B5";
				unknownImg.style.top = this.resetImageHeight(90) + "px";
				unknownImg.style.left = this.resetImageWidth(10) + "px";
				unknownImg.style.width = this.resetImageWidth(20) + "px";
				unknownImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(unknownImg);

				var unknownTitle = document.createElement('div');
				unknownTitle.id = "unknown_Title";
				unknownTitle.style.float = "right";
				unknownTitle.style.position = "absolute";
				unknownTitle.style.top = this.resetImageHeight(90) + "px";
				unknownTitle.style.left = this.resetImageWidth(35) + "px";
				unknownTitle.style.width = this.resetImageWidth(65) + "px";
				unknownTitle.style.height = this.resetImageHeight(20) + "px";
				unknownTitle.innerHTML = "service_unknown";
				legendDiv.appendChild(unknownTitle);

				// service_broken
				var brokenImg = document.createElement('div');
				brokenImg.id = "broken_Img";
				brokenImg.style.float = "left";
				brokenImg.style.position = "absolute";
				brokenImg.style.backgroundColor = "#FF0000";
				brokenImg.style.top = this.resetImageHeight(120) + "px";
				brokenImg.style.left = this.resetImageWidth(10) + "px";
				brokenImg.style.width = this.resetImageWidth(20) + "px";
				brokenImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(brokenImg);

				var brokenTitle = document.createElement('div');
				brokenTitle.id = "broken_Title";
				brokenTitle.style.float = "right";
				brokenTitle.style.position = "absolute";
				brokenTitle.style.top = this.resetImageHeight(120) + "px";
				brokenTitle.style.left = this.resetImageWidth(35) + "px";
				brokenTitle.style.width = this.resetImageWidth(65) + "px";
				brokenTitle.style.height = this.resetImageHeight(20) + "px";
				brokenTitle.innerHTML = "service_broken";
				legendDiv.appendChild(brokenTitle);

				// service_closing
				var closingImg = document.createElement('div');
				closingImg.id = "closing_Img";
				closingImg.style.float = "left";
				closingImg.style.position = "absolute";
				closingImg.style.backgroundColor = "#EEDC82";
				closingImg.style.top = this.resetImageHeight(150) + "px";
				closingImg.style.left = this.resetImageWidth(10) + "px";
				closingImg.style.width = this.resetImageWidth(20) + "px";
				closingImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(closingImg);

				var closingTitle = document.createElement('div');
				closingTitle.id = "closing_Title";
				closingTitle.style.float = "right";
				closingTitle.style.position = "absolute";
				closingTitle.style.top = this.resetImageHeight(150) + "px";
				closingTitle.style.left = this.resetImageWidth(35) + "px";
				closingTitle.style.width = this.resetImageWidth(65) + "px";
				closingTitle.style.height = this.resetImageHeight(20) + "px";
				closingTitle.innerHTML = "service_closing";
				legendDiv.appendChild(closingTitle);

				// service_shutdown
				var shutdownImg = document.createElement('div');
				shutdownImg.id = "shutdown_Img";
				shutdownImg.style.float = "left";
				shutdownImg.style.position = "absolute";
				shutdownImg.style.backgroundColor = "#CD853F";
				shutdownImg.style.top = this.resetImageHeight(180) + "px";
				shutdownImg.style.left = this.resetImageWidth(10) + "px";
				shutdownImg.style.width = this.resetImageWidth(20) + "px";
				shutdownImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(shutdownImg);

				var shutdownTitle = document.createElement('div');
				shutdownTitle.id = "shutdown_Title";
				shutdownTitle.style.float = "right";
				shutdownTitle.style.position = "absolute";
				shutdownTitle.style.top = this.resetImageHeight(180) + "px";
				shutdownTitle.style.left = this.resetImageWidth(35) + "px";
				shutdownTitle.style.width = this.resetImageWidth(65) + "px";
				shutdownTitle.style.height = this.resetImageHeight(20) + "px";
				shutdownTitle.innerHTML = "service_shutdown";
				legendDiv.appendChild(shutdownTitle);

				// service_ready
				var readyImg = document.createElement('div');
				readyImg.id = "ready_Img";
				readyImg.style.float = "left";
				readyImg.style.position = "absolute";
				readyImg.style.backgroundColor = "#AB82FF";
				readyImg.style.top = this.resetImageHeight(210) + "px";
				readyImg.style.left = this.resetImageWidth(10) + "px";
				readyImg.style.width = this.resetImageWidth(20) + "px";
				readyImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(readyImg);

				var readyTitle = document.createElement('div');
				readyTitle.id = "ready_Title";
				readyTitle.style.float = "right";
				readyTitle.style.position = "absolute";
				readyTitle.style.top = this.resetImageHeight(210) + "px";
				readyTitle.style.left = this.resetImageWidth(35) + "px";
				readyTitle.style.width = this.resetImageWidth(65) + "px";
				readyTitle.style.height = this.resetImageHeight(20) + "px";
				readyTitle.innerHTML = "service_ready";
				legendDiv.appendChild(readyTitle);

				// UNCONFIG
				var unconfigImg = document.createElement('div');
				unconfigImg.id = "unconfig_Img";
				unconfigImg.style.float = "left";
				unconfigImg.style.position = "absolute";
				unconfigImg.style.backgroundColor = "#B5B5B5";
				unconfigImg.style.top = this.resetImageHeight(240) + "px";
				unconfigImg.style.left = this.resetImageWidth(10) + "px";
				unconfigImg.style.width = this.resetImageWidth(20) + "px";
				unconfigImg.style.height = this.resetImageHeight(20) + "px";
				legendDiv.appendChild(unconfigImg);

				var unconfigTitle = document.createElement('div');
				unconfigTitle.id = "unconfig_Title";
				unconfigTitle.style.float = "right";
				unconfigTitle.style.position = "absolute";
				unconfigTitle.style.top = this.resetImageHeight(240) + "px";
				unconfigTitle.style.left = this.resetImageWidth(35) + "px";
				unconfigTitle.style.width = this.resetImageWidth(65) + "px";
				unconfigTitle.style.height = this.resetImageHeight(20) + "px";
				unconfigTitle.innerHTML = "UNCONFIG";
				legendDiv.appendChild(unconfigTitle);

				Ext.get(this.mainDragZoneId).dom.appendChild(legendDiv);

			},
			/**
			 * 设置坐标
			 *
			 * @param o{}
			 *            id
			 * @param o{}
			 *            x
			 * @param o{}
			 *            y
			 */
			setNodePosition : function(o) {
				try {
					if (this.nodeRelationArrObj[o.id]) {
						this.nodeRelationArrObj[o.id].x = o.x;
						this.nodeRelationArrObj[o.id].y = o.y;
					}
				} catch (e) {
					gvlog(e)
				}
			},
			/**
			 * 线条初始化<br/> 将节点数据转换成画线数据-开始点和结束点坐标对象 并画出线
			 */
			drawAllLine : function() {
				this.clearAllLine();
				for (var resId in this.nodeRelationArrObj) { // 遍历节点数据
					if (!this.nodeRelationArrObj[resId]) { // 不存在就继续
						continue;
					}
					if (this.nodeRelationArrObj[resId].isSNode) {
						var node = this.nodeRelationArrObj[resId];
						var relationArr = this.nodeRelationArrObj[resId].relArr;
						var sdeviationXY = this.cf.deviationObj[this.nodeRelationArrObj[resId].cls];
						var startx = node.x * 1 + sdeviationXY.x * 1;
						var starty = node.y * 1 + sdeviationXY.y * 1;
						var endx = 0;
						var endy = 0;
						for (var m = 0; m < relationArr.length; m++) { // 根据关系设置画线数据
							eval('var id=relationArr[m].'
									+ this.endNodeFieldName);
							eval('var port=relationArr[m].'
									+ this.portFieldName);
							eval('var status=relationArr[m].'
									+ this.portStatusFieldName);
							if (!this.nodeRelationArrObj[id]) { // 不存在就继续
								continue;
							}
							var edeviationXY = this.cf.deviationObj[this.nodeRelationArrObj[id].cls];
							var enode = this.getNodeDataById(id);
							endx = enode.x * 1 + edeviationXY.x * 1;
							endy = enode.y * 1 + edeviationXY.y * 1;
							var pid = this.prefix + resId + "_port_" + port;
							// 端口信息
							var param1 = {
								status : status,
								id : pid,
								x : (startx + endx) / 2 - 30,
								y : (starty + endy) / 2,
								z : node.z,
								port : port
							}
							this.displayPortNum(param1);
							// 画线信息
							var param = {
								status : status,
								startx : startx,
								starty : starty,
								endx : endx,
								endy : endy
							}
							this.drawSingleLine(param);
						}
					}
				}

			},
			addoParaLine : function(haPairs, haId) {
				var node1 = haPairs["left"];
				var node2 = haPairs["right"];
				var startx1 = node1.x;
				var starty1 = node1.y;
				var endx1 = node2.x;
				var endy1 = node2.y;

				var xpings = this.resetImageWidth(215);
				var ypings = this.resetImageHeight(35);
				var xpinge = this.resetImageWidth(195);
				var ypinge = this.resetImageHeight(35);
				var yseria = this.resetImageHeight(60);
				var yha = this.resetImageHeight(10);

				// 左添加PING状态
				var contentnetPingleft = document.createElement('div');
				contentnetPingleft.style.zIndex = node1.z;
				contentnetPingleft.id = node1.id + '_leftPing_contentDivId';
				contentnetPingleft.className = node1.cls;
				contentnetPingleft.style.position = "absolute";
				contentnetPingleft.style.left = startx1 + xpings + "px";
				contentnetPingleft.style.top = starty1 + ypings + "px";
				contentnetPingleft.style.width = (endx1 + startx1 + xpinge) / 2
						- (startx1 + xpings) + "px";
				contentnetPingleft.style.height = '8px';

				var pingStatusLeft = document.createElement("img");
				pingStatusLeft.id = node1.id + "_netStatus_img";

				var strStatus = node1.nodeStatus.pingStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}

				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
				
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}

				pingStatusLeft.src = this.imageUrl + "line_" + strStatus
						+ ".gif";// 此处需要根据node1节点ping状态来显示哪种图片

				pingStatusLeft.style.zIndex = node1.z;
				pingStatusLeft.style.width = (endx1 + startx1 + xpinge) / 2
						- (startx1 + xpings) + "px";
				pingStatusLeft.style.height = '8px';
				pingStatusLeft.alt = gvI18n("network_ping_status") + "："
						+ node1.nodeStatus.pingStatus;
				pingStatusLeft.title = gvI18n("network_ping_status") + "："
						+ node1.nodeStatus.pingStatus;
				contentnetPingleft.appendChild(pingStatusLeft);

				// 左添加串口状态
				var contentnetSerialleft = document.createElement('div');
				contentnetSerialleft.style.zIndex = node1.z;
				contentnetSerialleft.id = node1.id + '_leftSerial_contentDivId';
				contentnetSerialleft.className = node1.cls;
				contentnetSerialleft.style.position = "absolute";
				contentnetSerialleft.style.left = startx1 + xpings + "px";
				contentnetSerialleft.style.top = starty1 + yseria + "px";
				contentnetSerialleft.style.width = (endx1 + startx1 + xpinge)
						/ 2 - (startx1 + xpings) + "px";
				contentnetSerialleft.style.height = '8px';

				var serialStatusLeft = document.createElement("img");
				serialStatusLeft.id = node1.id + "_netStatus_img";
				var strStatus = node1.nodeStatus.serialStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}

				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null &&  strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}

				serialStatusLeft.src = this.imageUrl + "line_" + strStatus
						+ ".gif";// 此处需要根据node1节点串口状态来显示哪种图片：状态包括
				// ok
				// fail
				// 两种
				serialStatusLeft.style.zIndex = node1.z;
				serialStatusLeft.style.width = (endx1 + startx1 + xpinge) / 2
						- (startx1 + xpings) + "px";
				serialStatusLeft.style.height = '8px';
				serialStatusLeft.alt = gvI18n("serial_status") + "："
						+ node1.nodeStatus.serialStatus;
				serialStatusLeft.title = gvI18n("serial_status") + "："
						+ node1.nodeStatus.serialStatus;
				contentnetSerialleft.appendChild(serialStatusLeft);

				// 左添加高可用状态
				var contentnetHAleft = document.createElement('div');
				contentnetHAleft.style.zIndex = node1.z;
				contentnetHAleft.id = node1.id + '_leftHA_contentDivId';
				contentnetHAleft.className = node1.cls;
				contentnetHAleft.style.position = "absolute";
				contentnetHAleft.style.left = startx1 + xpings + "px";
				contentnetHAleft.style.top = starty1 + "px";
				contentnetHAleft.style.width = '16px';
				contentnetHAleft.style.height = '16px';

				var haStatusLeft = document.createElement("img");
				haStatusLeft.id = node1.id + "_haStatus_img";
				var strStatus = node1.nodeStatus.haStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}

				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}

				haStatusLeft.src = this.imageUrl + strStatus + ".gif";// 此处需要根据node1节点状态来显示哪种图片：状态包括
				// ok
				// fail
				// 两种
				haStatusLeft.style.zIndex = node1.z;
				haStatusLeft.style.width = '16px';
				haStatusLeft.style.height = '16px';
				haStatusLeft.alt = gvI18n("ha_status") + "："
						+ node1.nodeStatus.haStatus;
				haStatusLeft.title = gvI18n("ha_status") + "："
						+ node1.nodeStatus.haStatus;
				contentnetHAleft.appendChild(haStatusLeft);

				var xpings = this.resetImageWidth(205);
				var ypings = this.resetImageHeight(35);
				var xpinge = this.resetImageWidth(5);
				var ypinge = this.resetImageHeight(35);
				var yserial = this.resetImageHeight(60);
				var yha = this.resetImageHeight(10);
				var xha = this.resetImageWidth(20);
				// 右添加PING状态
				var contentnetPingRight = document.createElement('div');
				contentnetPingRight.style.zIndex = node2.z;
				contentnetPingRight.id = node2.id + '_rightPing_contentDivId';
				contentnetPingRight.className = node2.cls;
				contentnetPingRight.style.position = "absolute";
				contentnetPingRight.style.left = (startx1 + xpings + endx1) / 2
						+ "px";
				contentnetPingRight.style.top = starty1 + ypings + "px";
				contentnetPingRight.style.width = (endx1 - xpinge)
						- (startx1 + xpings + endx1) / 2 + "px";
				contentnetPingRight.style.height = '8px';

				var pingimgRight = document.createElement("img");
				pingimgRight.id = node2.id + "_netStatus_img";
				var strStatus = node2.nodeStatus.pingStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}
				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}

				pingimgRight.src = this.imageUrl + "line_" + strStatus + ".gif";// 此处需要根据node2节点ping状态来显示哪种图片：状态包括
				// ok
				// fail
				// 两种
				pingimgRight.style.zIndex = node2.z;
				pingimgRight.style.width = (endx1 - xpinge)
						- (startx1 + xpings + endx1) / 2 + "px";
				pingimgRight.style.height = '8px';
				pingimgRight.alt = gvI18n("network_ping_status") + "："
						+ node2.nodeStatus.pingStatus;
				pingimgRight.title = gvI18n("network_ping_status") + "："
						+ node2.nodeStatus.pingStatus;
				contentnetPingRight.appendChild(pingimgRight);

				// 右添加串口状态
				var contentnetSerialRight = document.createElement('div');
				contentnetSerialRight.style.zIndex = node2.z;
				contentnetSerialRight.id = node2.id
						+ '_rightSerial_contentDivId';
				contentnetSerialRight.className = node2.cls;
				contentnetSerialRight.style.position = "absolute";
				contentnetSerialRight.style.left = (startx1 + xpings + endx1)
						/ 2 + "px";
				contentnetSerialRight.style.top = starty1 + yserial + "px";
				contentnetSerialRight.style.width = (endx1 - xpinge)
						- (startx1 + xpings + endx1) / 2 + "px";
				contentnetSerialRight.style.height = '8px';

				var serialimgRight = document.createElement("img");
				serialimgRight.id = node2.id + "_netStatus_img";
				var strStatus = node2.nodeStatus.serialStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}
				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}
				serialimgRight.src = this.imageUrl + "line_" + strStatus
						+ ".gif";// 此处需要根据node2节点串口状态来显示哪种图片：状态包括
				// ok
				// fail
				// 两种
				serialimgRight.style.zIndex = node2.z;
				serialimgRight.style.width = (endx1 - xpinge)
						- (startx1 + xpings + endx1) / 2 + "px";
				serialimgRight.style.height = '8px';
				serialimgRight.alt = gvI18n("serial_status") + "："
						+ node2.nodeStatus.serialStatus;
				serialimgRight.title = gvI18n("serial_status") + "："
						+ node2.nodeStatus.serialStatus;
				contentnetSerialRight.appendChild(serialimgRight);

				// 右添加高可用状态
				var contentnetHARight = document.createElement('div');
				contentnetHARight.style.zIndex = node1.z;
				contentnetHARight.id = node2.id + '_leftHA_contentDivId';
				contentnetHARight.className = node2.cls;
				contentnetHARight.style.position = "absolute";
				contentnetHARight.style.left = endx1 - xha + "px";
				contentnetHARight.style.top = starty1 + "px";
				contentnetHARight.style.width = '16px';
				contentnetHARight.style.height = '16px';

				var haStatusRight = document.createElement("img");
				haStatusRight.id = node2.id + "_haStatus_img";
				var strStatus = node2.nodeStatus.haStatus;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}
				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}
				haStatusRight.src = this.imageUrl + strStatus + ".gif";// 此处需要根据node2节点高可用状态来显示哪种图片：状态包括
				// ok
				// fail
				// 两种
				haStatusRight.style.zIndex = node2.z;
				haStatusRight.style.width = '16px';
				haStatusRight.style.height = '16px';
				haStatusRight.alt = gvI18n("ha_status") + "："
						+ node2.nodeStatus.haStatus;
				haStatusRight.title = gvI18n("ha_status") + "："
						+ node2.nodeStatus.haStatus;
				contentnetHARight.appendChild(haStatusRight);

				Ext.get(this.mainDragZoneId).dom
						.appendChild(contentnetPingleft);
				Ext.get(this.mainDragZoneId).dom
						.appendChild(contentnetPingRight);
				Ext.get(this.mainDragZoneId).dom
						.appendChild(contentnetSerialleft);
				Ext.get(this.mainDragZoneId).dom
						.appendChild(contentnetSerialRight);
				Ext.get(this.mainDragZoneId).dom.appendChild(contentnetHAleft);
				Ext.get(this.mainDragZoneId).dom.appendChild(contentnetHARight);
			},
			/**
			 * 画oPara之间连线
			 *
			 */
			drawoParasLine : function(haPairs, haId) {

				var node1 = haPairs["left"];
				var node2 = haPairs["right"];
				var startx1 = node1.x;
				var starty1 = node1.y;
				var endx1 = node2.x;
				var endy1 = node2.y;
				// 画线信息
				// var param1 = {
				// status: node1.nodeStatus.ethA,
				// startx: startx1 + 215,
				// starty: starty1 + 10,
				// endx: (endx1 + startx1+ 195)/2,
				// endy: endy1 + 10,
				// lineWidth: 8,
				// lineCap: 'round'
				// }
				// this.drawSingleLine(param1);
				var xs = this.resetImageWidth(215);
				var ys = this.resetImageHeight(35);
				var xe = this.resetImageWidth(195);
				var ye = this.resetImageHeight(35);
				// 画线信息
				var param2 = {
					status : node1.nodeStatus.serialLine,
					startx : startx1 + xs,
					starty : starty1 + ys,
					endx : (endx1 + startx1 + xe) / 2,
					endy : endy1 + ye,
					lineWidth : 8,
					lineCap : 'round'
				}
				this.drawSingleLine(param2);

				// //画线信息
				// var param3 = {
				// status: node1.nodeStatus.ethB,
				// startx: startx1 + 215,
				// starty: starty1 + 60,
				// endx: (endx1 + startx1+ 195)/2,
				// endy: endy1 + 60,
				// lineWidth: 8,
				// lineCap: 'round'
				// }
				// this.drawSingleLine(param3);

				// //画线信息
				// var param4 = {
				// status: node2.nodeStatus.ethA,
				// startx: (startx1 + 205 + endx1)/2,
				// starty: starty1 + 10,
				// endx: endx1 - 5 ,
				// endy: endy1 + 10,
				// lineWidth: 8,
				// lineCap: 'round'
				// }
				// this.drawSingleLine(param4);
				var xs = this.resetImageWidth(205);
				var ys = this.resetImageHeight(35);
				var xe = this.resetImageWidth(5);
				var ye = this.resetImageHeight(35);
				// 画线信息
				var param5 = {
					status : node2.nodeStatus.serialLine,
					startx : (startx1 + xs + endx1) / 2,
					starty : starty1 + ys,
					endx : endx1 - xe,
					endy : endy1 + ye,
					lineWidth : 8,
					lineCap : 'round'
				}
				this.drawSingleLine(param5);

				// //画线信息
				// var param6 = {
				// status: node2.nodeStatus.ethB,
				// startx: (startx1 + 205+ endx1)/2,
				// starty: starty1 + 60,
				// endx: endx1 - 5,
				// endy: endy1 + 60,
				// lineWidth: 8,
				// lineCap: 'round'
				// }
				// this.drawSingleLine(param6);

			},
			/**
			 * 画单条线
			 *
			 * @param o{}
			 *            startx
			 * @param o{}
			 *            starty
			 * @param o{}
			 *            strokeStyle
			 * @param o{}
			 *            lineCap(线条的端点类型可以是butt(默认),round-圆头和square-方头)
			 * @param o{}
			 *            lineJoin(连接线的类型：round,bevel和miter(默认))
			 * @param o{}
			 *            endx
			 * @param o{}
			 *            endy
			 */
			drawSingleLine : function(o) {
				// if (!o.strokeStyle) {
				// o.strokeStyle = '#EB8114';
				// }
				if (!o.lineJoin) {
					o.lineJoin = 'round';
				}
				if (!o.lineCap) {
					o.lineCap = 'round';
				}
				if (!o.lineWidth) {
					o.lineWidth = 2;
				}
				if (o.status == "down") {
					o.strokeStyle = '#ff0000';
				} else {
					o.strokeStyle = '#02B050';
				}
				var mainGraph = Ext.get(this.mainCanvasId).dom.getContext('2d');
				mainGraph.beginPath();
				mainGraph.moveTo(o.startx, o.starty);
				mainGraph.strokeStyle = o.strokeStyle;
				mainGraph.lineWidth = o.lineWidth;
				mainGraph.lineJoin = o.lineJoin; // miter,round,bevel ，
				mainGraph.lineCap = o.lineCap; // 'butt','round','square'，
				mainGraph.lineTo(o.endx, o.endy);
				mainGraph.stroke();
				mainGraph.closePath();
			},
			/**
			 * 清除全部画线
			 */
			clearAllLine : function() {
				var mainGraph = document.getElementById(this.mainCanvasId)
						.getContext('2d');
				mainGraph.clearRect(0, 0, this.width, this.dashboardHeight);
			},
			/**
			 * 创建DD对象
			 */
			createDD : function(id) {
				var overrides = {
					b4StartDrag : function() {
						informationcollector_displayTopology_canvasPanelme.isOnDrag = true;
						if (!this.el) {
							this.el = Ext.get(this.getEl());
						}

						// Cache the original XY Coordinates of the element,
						// we'll
						// use this later.
						this.originalXY = this.el.getXY();
						this.startXY = {};
						this.startXY.x = this.el.dom.style.left;
						this.startXY.y = this.el.dom.style.top;
						// Ext.get('output').update('x:'+this.startXY.x+'
						// y:'+this.startXY.y);
					},
					// drag event
					onDrag : function() {
						informationcollector_displayTopology_canvasPanelme.isOnDrag = true;
						var x = this.el.dom.style.left.substring(0,
								this.el.dom.style.left.length - 2);
						var y = this.el.dom.style.top.substring(0,
								this.el.dom.style.top.length - 2);
						var o = {
							id : this.el.dom.id,
							x : x,
							y : y
						};
						informationcollector_displayTopology_canvasPanelme
								.setNodePosition(o);
						informationcollector_displayTopology_canvasPanelme
								.drawAllLine();

					},
					// 不在一个拖放组里面的就是无效 drop。
					onInvalidDrop : function() {
						this.invalidDrop = true;
					},
					// endDrag event
					endDrag : function() {
						// 无效置下真正的逻辑 Invoke the animation if the invalidDrop
						// flag is
						// set to true
						if (this.invalidDrop === true) {
							var animCfgObj = {
								easing : 'elasticOut',
								duration : 1,
								scope : this,
								callback : function() {
									// Remove the position attribute
									this.el.dom.style.position = '';
									this.el.dom.style.position = 'absolute';
									this.el.dom.style.top = this.startXY.y;
									this.el.dom.style.left = this.startXY.x;
								}
							};
							this.el.moveTo(this.originalXY[0],
									this.originalXY[1], animCfgObj);
							delete this.invalidDrop;
						}
						informationcollector_displayTopology_canvasPanelme.isOnDrag = false;
					}

				};
				var dd = new Ext.dd.DD(id, 'DDGroup', {
							isTarget : false
						});
				Ext.apply(dd, overrides);
				this.ddArrObj[id] = dd;
			},
			/**
			 * 数据加载
			 */
			// loadData: function () {
			// this.clearAllNode();
			// this.hiddenInfoPanel();
			// var tempUrl = "";
			// if (this.currentCanvasId != "") {
			// tempUrl = this.baseDataUrl + "?canId=" + this.currentCanvasId;
			// } else {
			// tempUrl = this.baseDataUrl;
			// }
			// var json = ExtTools.getResponseJson(tempUrl, "");
			// gvlog(json);
			// this.nodeRelationArrObj = json.relationData;
			// //this.nodeIdsArr = json.nodeIdsArr;
			// this.maxZindex = json.maxZindex
			// },
			/**
			 * 数据加载
			 */
			loadData : function() {
				this.clearAllNode();
				// this.hiddenInfoPanel();
				var tempUrl = "";
				if (this.currentCanvasId != "") {
					tempUrl = this.baseDataUrl + "?canId="
							+ this.currentCanvasId;
				} else {
					tempUrl = this.baseDataUrl;
				}
				var json = ExtTools.getResponseJson(tempUrl, "");
				this.allHAoParaPairs = json;
				// for(var haId in json) {
				// this.haoParas = json[haId];
				// }
				// this.nodeRelationArrObj = json;
			},
			/**
			 * oStor数据加载
			 */
			loadoStorData : function() {
				var tempUrl = "";
				tempUrl = this.ostorDataUrl;
				var json = ExtTools.getResponseJson(tempUrl, "");
				this.allHAoStors = json;
			},

			/**
			 *
			 * 存储系统监控数据加载
			 */
			loadStorSystemData : function() {
				var tempUrl = "";
				tempUrl = this.storSystemDataUrl;
				var json = ExtTools.getResponseJson(tempUrl, "");
				this.storSystemData = json;
			},
			/**
			 * 根据Id得到节点数据
			 */
			getNodeDataById : function(id) {
				var d = null;
				if (this.nodeRelationArrObj[id]) {
					d = this.nodeRelationArrObj[id];
				}
				return d;
			},
			/**
			 * 锁定
			 */
			lockAll : function() {
				for (var i = 0; i < this.nodeIdsArr.length; i++) {
					this.ddArrObj[this.nodeIdsArr[i]].lock();

				}
			},
			/**
			 * 解锁锁定
			 */
			unlockAll : function() {
				for (var i = 0; i < this.nodeIdsArr.length; i++) {
					this.ddArrObj[this.nodeIdsArr[i]].unlock();

				}
			},
			/**
			 * 刷新<br>
			 * 注意：节点初始化要在画线前面，因为将nodeRelationArrObj中的XY坐标由原来的百分比转成真是的像素坐标<br>
			 */
			refreshData : function() {
				if (this.isRender) {
					this.loadData();
					this.initCanvasSize();
					// must before drawAllLine
					this.clearAllLine();
					for (var haId in this.allHAoParaPairs) {
						var haoParaPair = this.allHAoParaPairs[haId];
						this.initNode(haoParaPair, haId);
						// this.drawAllLine();
						// this.drawoParasLine(haoParaPair, haId)
						this.addoParaLine(haoParaPair, haId);
					}
					this.loadoStorData();
					this.initoStorNode();
					this.loadStorSystemData();

					this.createStorSystemMonitorInfo();
				}
			},

			/**
			 * 判断页面元素是否存在
			 */
			isHasElement : function(id) {
				var flag = false;
				if (Ext.get(id)) {
					flag = true;
				}
				return flag;
			},
			/**
			 * 根据事件得到事件源
			 */
			getSourceEl : function(evt) {
				evt = evt ? evt : (window.event ? window.event : null);
				var oTarget = evt.target || evt.srcElement;
				if (oTarget.id.indexOf('_contentDivId') > -1) {
					oTarget = oTarget.parentNode;
				} else if (oTarget.id.indexOf('_titleDivId') > -1) {
					oTarget = oTarget.parentNode;
				} else if (oTarget.id.indexOf('_img') > -1) {
					if (oTarget.id.indexOf('_alarm_img') > -1) {
						oTarget = oTarget.parentNode
					} else {
						oTarget = oTarget.parentNode.parentNode;
					}
				} else if (oTarget.id.indexOf('_subGraphID') > -1) {
					oTarget = oTarget.parentNode;
				} else if (oTarget.id == undefined || oTarget.id == null
						|| oTarget.id == "") {
					if (Ext.isIE) {
						return null;
					}
				}
				return oTarget;
			},
			/**
			 * 创建画布
			 */
			createMainCanvas : function() {
				// this.getEl(this.mainDragZoneId).
				// this.getEl(this.mainDragZoneId).style.backgroundColor =
				// "#f3f9ff";
				this.getEl(this.mainDragZoneId).style.backgroundImage = 'url("'
						+ this.imageUrl + 'canvas_bg.gif?' + Math.random()
						+ '")';
				this.getEl(this.mainDragZoneId).style.zIndex = 0;
				var canvas = document.createElement('canvas');
				canvas.id = this.mainCanvasId;

				// canvas.style.zIndex=2;
				// canvas.style.borderRight="1px solid #ddd";
				canvas.style.zIndex = 1;
				this.getEl(this.mainDragZoneId).appendChild(canvas);
				this.createLegend();

				// this.createStorSystemMonitorInfo();

				this.ieVersionCompatible(canvas);
			},
			/**
			 * 兼容IE7\8版本
			 */
			ieVersionCompatible : function(canvas) {
				var browser = navigator.appName
				var b_version = navigator.appVersion
				var version = b_version.split(";");
				var trim_Version = "";
				if (Ext.isIE) {
					trim_Version = version[1].replace(/[ ]/g, "");
					if (trim_Version == "MSIE7.0" || trim_Version == "MSIE8.0"
							|| trim_Version == "MSIE9.0") {
						this.mainCanvas = window.G_vmlCanvasManager
								.initElement(canvas);
						G_vmlCanvasManager.init_(document);
					}
				}
			},
			/**
			 * 根据提示框的宽高得到信息框XY坐标（依据鼠标位置判断，如果提示框出界将自动移到机房内显示）
			 *
			 * @param o
			 *            详细数据
			 * @param w
			 *            提示框宽
			 * @param h
			 *            提示框高
			 */
			getInfoPanelXYPostionByWH : function(o, w, h) {
				var x = 0;
				var y = 0;
				var wh = this.getDivWHById(this.prefix + o.resId);
				// 判断x坐标是否超出机房边界
				if ((o.infoPanelX * 1 + w * 1) > this.dashboardWidth * 1) {
					x = o.infoPanelX * 1 - wh.w - w * 1;
				} else {
					x = o.infoPanelX * 1;
				}
				// 判断y坐标是否超出机房边界
				if ((o.infoPanelY * 1 + h * 1) > this.dashboardHeight * 1) {
					y = o.infoPanelY * 1 - h * 1;
				} else {
					y = o.infoPanelY * 1;
				}
				// 在IE有偏差，进行修正
				if (Ext.isIE) {
					// x+=10;
				}
				return {
					'x' : x,
					'y' : y
				};
			},
			/**
			 * 得到显示的XY坐标
			 */
			getDisplayXY : function(o) {
				var tx = 0;
				var ty = 0;
				if (o.x && o.y) {
					tx = o.x * 1 / this.xyAccurate * this.dashboardWidth * 1;
					ty = o.y * 1 / this.xyAccurate * this.dashboardHeight * 1;
				}
				return {
					x : tx,
					y : ty
				};
			},
			/**
			 * 根据显示界面得到大小设置控件宽度
			 */
			resetImageWidth : function(width) {
				var twidth = width;
				twidth = width * this.dashboardWidth / 1920;
				return twidth;
			},
			/**
			 * 根据显示界面大小设置空间高度
			 */
			resetImageHeight : function(height) {
				var theight = height;
				theight = height * this.dashboardHeight / 828;
				return theight;
			},

			/**
			 * 得到图片宽度
			 */
			getRealWidth : function(width) {
				var rwidth = 0;
				rwidth = width / this.dashboardWidth * this.xyAccurate;
				return rwidth;
			},
			/**
			 * 将显示的XY坐标转化成存储坐标
			 */
			displayXYtransformSaveXY : function(o) {
				var tx = 0;
				var ty = 0;
				if (o.x && o.y) {
					tx = Math.round(o.x * 1 / (this.dashboardWidth * 1)
							* this.xyAccurate);
					ty = Math.round(o.y * 1 / (this.dashboardHeight * 1)
							* this.xyAccurate);
				}
				return {
					x : tx,
					y : ty
				};
			},
			/**
			 * 根据id得到DIV的宽高
			 */
			getDivWHById : function(id) {
				var tw = 0;
				var th = 0
				if (document.getElementById(id)) {
					tw = document.getElementById(id).offsetWidth;
					th = document.getElementById(id).offsetHeight;
				}
				return {
					w : tw,
					h : th
				};
			},
			/**
			 * 重新设置详细信息提示框DIV的ZIndex大小，防止挡住其他窗口显示及操作
			 */
			resetInfoPanelZIndex : function() {
				var infop = Ext.get(this.infoPanelId).dom;
				infop.style.zIndex = this.maxZindex * 1 + 1;
				var iframes = infop.parentNode.getElementsByTagName('iframe');
				for (var i = 0; i < iframes.length; i++) {
					iframes[i].style.zIndex = this.maxZindex * 1 + 1;
				}
			},
			/**
			 * 告警Gif图片
			 */
			delAlarmGif : function(id) {
				var id = id + '_alarm_img';
				if (Ext.get(id)) {
					Ext.get(id).dom.parentNode.removeChild(Ext.get(id).dom);
				}
			},
			/**
			 * 添加oStor告警图片
			 */
			createAlarmGif : function(id, alarmType) {
				var top;
				var imgid = id + '_alarm_img';
				var d = document.createElement("img");
				if (Ext.get(imgid)) {
					Ext.get(imgid).dom.parentNode
							.removeChild(Ext.get(imgid).dom);
				}
				var height = this.resetImageHeight(75);
				d.style.left = '0px';
				d.style.top = height + "px";
				d.style.position = "absolute";
				d.id = imgid;
				var strStatus = alarmType;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}
				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}
				d.src = this.imageUrl + strStatus + ".gif";
				// if(alarmType == "ok") {
				// d.src = this.imageUrl + "ok.gif";
				// }else if(alarmType != "ok") {
				// d.src = this.imageUrl + "fail.gif";
				// }

				d.style.width = '12px';
				d.style.height = '12px';
				d.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;
				Ext.get(id).dom.appendChild(d);
			},
			/**
			 * 添加oPara数据流图片
			 */
			addoParaINandOUTByteSpeed : function(oPara, displayXY) {

				var node = document.createElement('div');
				node.id = "oParaByteSpeedNode" + oPara.id;
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				var height = this.resetImageHeight(80);
				var width = this.resetImageWidth(50);
				node.style.top = displayXY.y + height + "px";
				node.style.left = displayXY.x + width + "px";

				var width = this.resetImageWidth(150)
				node.style.width = width + "px";
				node.style.zIndex = oPara.z;

				var content = document.createElement('div');
				content.style.zIndex = oPara.z;
				content.id = oPara.id + '_ByteSpeedDivId';
				content.className = oPara.cls;
				content.style.position = "absolute";
				content.style.top = 0 + "px";
				content.style.left = 0 + "px";
				var width = this.resetImageWidth(100)
				content.style.width = width + "px";

				// var titlein = document.createElement('div');
				// titlein.id = oPara.id + '_intitleDivId';
				// titlein.innerHTML = oPara.nodeStatus.inRate;
				// titlein.style.zIndex = oPara.z;
				// titlein.style.position="absolute";
				// titlein.style.top = 0 + "px";
				// titlein.style.left = 0 + "px";
				// content.appendChild(titlein);

				var outimg = document.createElement("img");
				outimg.id = oPara.id + "_byteOut_img";

				// img.src = this.imageUrl + oStor.cls + ".png";
				// inimg.src = this.imageUrl +
				// "arrowdown"+oPara.nodeStatus.displayOutRate+".gif";
				outimg.src = this.imageUrl + "arrowdown0.gif";
				outimg.alt = gvI18n("Data Read Speed") + "："
						+ oPara.nodeStatus.outRate + "MB/s";
				outimg.title = gvI18n("Data Read Speed") + "："
						+ oPara.nodeStatus.outRate + "MB/s";
				outimg.style.zIndex = oPara.z;
				outimg.style.position = "absolute";
				var width = this.resetImageWidth(20);
				outimg.style.width = width + "px";
				outimg.style.height = this.oParaoStorHeight * 1
						/ this.xyAccurate * this.dashboardHeight * 1 + "px";
				var width = this.resetImageWidth(20)
				outimg.style.left = width + "px";
				content.appendChild(outimg);

				// var titleout = document.createElement('div');
				// titleout.id = oPara.id + '_outtitleDivId';
				// titleout.innerHTML = oPara.nodeStatus.outRate;
				// titleout.style.zIndex = oPara.z;
				// titleout.style.position="absolute";
				// titleout.style.top = 0 + "px";
				// titleout.style.right = 0 + "px";
				// content.appendChild(titleout);

				var inimg = document.createElement("img");
				inimg.id = oPara.id + "_byteIn_img";
				inimg.style.position = "absolute";
				// img.src = this.imageUrl + oStor.cls + ".png";
				// outimg.src = this.imageUrl +
				// "arrowup"+oPara.nodeStatus.displayInRate+".gif";
				inimg.src = this.imageUrl + "arrowup0.gif";
				inimg.alt = gvI18n("Data Write Speed") + "："
						+ oPara.nodeStatus.inRate + "MB/s";
				inimg.title = gvI18n("Data Write Speed") + "："
						+ oPara.nodeStatus.inRate + "MB/s";
				inimg.style.zIndex = oPara.z;
				var width = this.resetImageWidth(20);
				inimg.style.width = width + "px";
				inimg.style.height = this.oParaoStorHeight * 1
						/ this.xyAccurate * this.dashboardHeight * 1 + "px";
				var width = this.resetImageWidth(15);
				inimg.style.right = width + "px";

				content.appendChild(inimg);

				node.appendChild(content);

				Ext.get(this.mainDragZoneId).dom.appendChild(node);
			},
			/**
			 * 添加oStor数据流入图片
			 */
			createINByteSpeed : function(id, oStor) {

				var node = document.createElement('div');
				node.id = id + "_inByteSpped_div";
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				var width = this.resetImageWidth(8);
				var height = this.resetImageHeight(40);
				node.style.top = height + "px";
				node.style.left = width + "px";

				var width = this.resetImageWidth(35);
				var height = this.resetImageHeight(40);
				node.style.width = width + "px";
				node.style.height = height + "px";
				node.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				// var title = document.createElement('div');
				// title.id = node.id + '_titleDivId';
				// title.innerHTML = oStor.nodeStatus.inRate;
				// title.style.zIndex = node.style.zIndex =
				// Ext.get(id).dom.style.zIndex * 1 + 1;
				// title.style.position="absolute";
				// title.style.bottom = 0 + "px";
				// title.style.left = 0 + "px";
				//
				// node.appendChild(title);

				var imgid = id + "_inByteSpeed_img";
				var d = document.createElement("img");
				if (Ext.get(imgid)) {
					Ext.get(imgid).dom.parentNode
							.removeChild(Ext.get(imgid).dom);
				}
				var width = this.resetImageWidth(20);
				d.style.right = 0 + "px";
				d.style.top = '0px';
				d.style.position = "absolute";
				d.id = imgid;
				// d.src = this.imageUrl +
				// "arrowdown"+oStor.nodeStatus.displayInRate+".gif";
				d.src = this.imageUrl + "arrowdown0.gif";
				d.alt = gvI18n("Data Write Speed") + "："
						+ oStor.nodeStatus.inRate + "MB/s";
				d.title = gvI18n("Data Write Speed") + "："
						+ oStor.nodeStatus.inRate + "MB/s";
				var width = this.resetImageWidth(15);
				var height = this.resetImageHeight(40);
				d.style.width = width + "px";
				d.style.height = height + "px";
				d.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				node.appendChild(d);
				Ext.get(id).dom.appendChild(node);
			},
			/**
			 * 添加oStor数据流出图片
			 */
			createOUTByteSpeed : function(id, oStor) {

				var node = document.createElement('div');
				node.id = id + "_outByteSpped_div";
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				var width = this.resetImageWidth(48);
				var height = this.resetImageHeight(40);
				node.style.top = height + "px";
				node.style.left = width + "px";

				var width = this.resetImageWidth(30);
				var height = this.resetImageHeight(40);
				node.style.width = width + "px";
				node.style.height = height + "px";
				node.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				// var title = document.createElement('div');
				// title.id = node.id + '_titleDivId';
				// title.innerHTML = oStor.nodeStatus.outRate;
				// title.style.zIndex = node.style.zIndex =
				// Ext.get(id).dom.style.zIndex * 1 + 1;
				// title.style.position="absolute";
				// title.style.bottom = 0 + "px";
				// title.style.right = 0 + "px";
				//
				// node.appendChild(title);

				var imgid = id + "_outByteSpeed_img";
				var d = document.createElement("img");
				if (Ext.get(imgid)) {
					Ext.get(imgid).dom.parentNode
							.removeChild(Ext.get(imgid).dom);
				}
				var width = this.resetImageWidth(50);
				d.style.left = 0 + "px";
				d.style.top = '0px';
				d.style.position = "absolute";
				d.id = imgid;
				// d.src = this.imageUrl +
				// "arrowup"+oStor.nodeStatus.displayInRate+".gif";
				d.src = this.imageUrl + "arrowup0.gif";
				d.alt = gvI18n("Data Read Speed") + "："
						+ oStor.nodeStatus.outRate + "MB/s";
				d.title = gvI18n("Data Read Speed") + "："
						+ oStor.nodeStatus.outRate + "MB/s";

				var width = this.resetImageWidth(15);
				var height = this.resetImageHeight(40);
				d.style.width = width + "px";
				d.style.height = height + "px";
				d.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				node.appendChild(d);
				Ext.get(id).dom.appendChild(node);
			},

			/**
			 * 添加oPara netroot图片
			 */
			createoParaNetRoot : function(oPara, oParaLeftDisplayXY) {
				var node = document.createElement('div');
				node.id = oPara.z + "_opara_netroot_div";
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				var width = this.resetImageWidth(75);
				node.style.top = oParaLeftDisplayXY.y
						+ this.resetImageHeight(210) + 'px';
				node.style.left = oParaLeftDisplayXY.x + width + "px";

				var width = this.resetImageWidth(35);
				var height = this.resetImageHeight(35);
				node.style.width = width + "px";
				node.style.height = height + "px";
				node.style.zIndex = oPara.z + 1;

				var imgid = oPara.z + "_opara_netroot_img";
				var d = document.createElement("img");
				if (Ext.get(imgid)) {
					Ext.get(imgid).dom.parentNode
							.removeChild(Ext.get(imgid).dom);
				}

				d.style.left = 0 + "px";
				d.style.top = '0px';
				d.style.position = "absolute";
				d.id = imgid;
				var isConnected = oPara.blConnectStatus;

				if (isConnected == true) {

					d.src = this.imageUrl + "netroot_down.png";
				} else {
					d.src = this.imageUrl + "netroot_down_unconnect.png";

					d.alt = gvI18n("can not direct oStor") + "："
							+ oPara.listUnConnectNodes;
					d.title = gvI18n("can not direct oStor") + "："
							+ oPara.listUnConnectNodes;
				}

				var width = this.resetImageWidth(45);
				var height = this.resetImageHeight(50);
				d.style.width = width + "px";
				d.style.height = height + "px";
				d.style.zIndex = oPara.z + 1;

				node.appendChild(d);
				Ext.get(this.mainDragZoneId).dom.appendChild(node);
			},

			/**
			 * 添加netroot图片
			 */
			createNetRoot : function(id, oStor) {
				var node = document.createElement('div');
				node.id = id + "_netroot_div";
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				var width = this.resetImageWidth(27);
				node.style.top = '0px';
				node.style.left = width + "px";

				var width = this.resetImageWidth(30);
				var height = this.resetImageHeight(40);
				node.style.width = width + "px";
				node.style.height = height + "px";
				node.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				var imgid = id + "_netroot_img";
				var d = document.createElement("img");
				if (Ext.get(imgid)) {
					Ext.get(imgid).dom.parentNode
							.removeChild(Ext.get(imgid).dom);
				}

				d.style.left = 0 + "px";
				d.style.top = '0px';
				d.style.position = "absolute";
				d.id = imgid;
				var isConnected = oStor.blConnectStatus;

				if (isConnected == true) {

					d.src = this.imageUrl + "netroot.png";
				} else {
					d.src = this.imageUrl + "netroot_unconnect.png";
					d.alt = gvI18n("can not direct oStor") + "："
							+ oStor.listUnConnectNodes;
					d.title = gvI18n("can not direct oStor") + "："
							+ oStor.listUnConnectNodes;
				}

				var width = this.resetImageWidth(35);
				var height = this.resetImageHeight(35);
				d.style.width = width + "px";
				d.style.height = height + "px";
				d.style.zIndex = Ext.get(id).dom.style.zIndex * 1 + 1;

				node.appendChild(d);
				Ext.get(id).dom.appendChild(node);
			},

			/**
			 * 创建页面NodeHTML字符串
			 */
			createNodeHTML : function() {
				// var nWH=this.cf.deviationObj[data.cls]
				// var
				// domStyle='position:absolute;top:'+displayXY.y+'px;left:'+displayXY.x
				// +'px;textAlign:center;z-inex:'+this.maxZindex+';width:'+nWH.w
				// + "px";
				// var dom='<div id="'+this.nodeIdsArr[i]'"
				// style="'+domStyle+'">';
				// var contentStyle='z-index:'+this.maxZindex;
				// dom+='<div id="'+this.nodeIdsArr[i]+ '_contentDivId'+'"
				// class="'+data.cls+'">';
				// dom+='<img id="'+this.nodeIdsArr[i] + '_img"
				// src="'+this.imageUrl +
				// data.cls + '".png">';
				// dom+='<div
				// id="'+this.nodeIdsArr[i]+'_titleDivId">'+data.title+'</div>';
				// dom+='</div></div>';
				// return dom;
			},
			clearAllNode : function() {
				document.getElementById(this.mainDragZoneId).innerHTML = "";
				this.createMainCanvas();
				this.initCanvasSize();
			},
			/**
			 * 创建双冗余画线
			 */
			createDoubleRedundancyLin : function(o) {
				var tmpUrl = this.doubleRedundancyLineUrl + "?resId=" + o.id
						+ "&canId=" + this.currentCanvasId;
				var json = ExtTools.getResponseJson(tmpUrl, "");
				var color = "#02B050"
				if (!json.status) {
					color = "#ff0000";
				}
				var sid = this.prefix + o.id + "_" + this.subCanvasId;
				var subGraph = document.createElement('canvas');
				subGraph.style.position = 'absolute';
				subGraph.style.left = '70px';
				subGraph.style.top = '0px';
				subGraph.width = 40;
				subGraph.height = 129;
				subGraph.id = sid;
				subGraph.style.zIndex = o.z * 1 + 1;
				Ext.get(this.prefix + o.id).dom.appendChild(subGraph);
				this.ieVersionCompatible(subGraph);
				var graph = Ext.get(sid).dom.getContext('2d');
				graph.beginPath();
				graph.moveTo(0, 70);
				graph.strokeStyle = color;
				graph.lineWidth = 8;
				graph.lineJoin = "bevel"; // miter,round,bevel ，
				graph.lineCap = "square"; // 'butt','round','square'，
				graph.lineTo(40, 70);
				graph.stroke();
				graph.closePath();
			},
			/**
			 * 显示端口号
			 */
			displayPortNum : function(o) {
				// 是否显示
				var isDis = false;
				if (o.status == "down") {
					isDis = true;
				}
				var el = this.getEl(o.id);
				if (!el) {
					if (isDis) {
						// var style='position:absolute;top:'+o.y+'px;left:'+o.x
						// +'px;textAlign:center;z-index:'+o.z+';width:30px;height:12px;';
						// var str='<div id="'+o.id+'"
						// style="'+style+'">'+o.port+'</div>';
						el = document.createElement("div");
						el.id = o.id;
						el.style.position = "absolute";
						el.style.left = o.x + 'px';
						el.style.top = o.y + 'px';
						el.style.zIndex = o.z;
						el.style.width = "30px";
						el.style.height = "12px";
						el.innerHTML = o.port;
						this.getEl(this.mainDragZoneId).appendChild(el);
					}
				} else {
					if (isDis) {
						el.style.top = o.y + 'px';
						el.style.left = o.x + 'px';
					} else {
						this.getEl(this.mainDragZoneId).removeChild(el);

					}
				}
			},
			getEl : function(id) {
				return document.getElementById(id);
			},
			/**
			 * 添加网线图图标
			 */
			addNetImageToCanvas : function(netImageY, netImageX) {

				var netNode = document.createElement('div');
				netNode.id = "netImage" + netImageY;
				netNode.style.clear = "both";
				netNode.style.position = "absolute";
				netNode.className = 'iconZone';
				netNode.style.top = netImageY + "px";

				netNode.cls = 'net';

				var nWH = this.cf.deviationObj[netNode.cls];
				var xrate = this.resetImageWidth(nWH.x);
				netNode.style.left = xrate + "px";
				var widthrate = this.resetImageWidth(nWH.w);
				// widthrate = widthrate* 1 / this.xyAccurate *
				// this.dashboardWidth * 1;
				netNode.style.width = widthrate + "px";
				netNode.style.zIndex = 0;

				var content = document.createElement('div');
				content.id = netNode.id + '_contentDivId';
				content.className = netNode.cls;
				content.style.position = "absolute";
				content.style.zIndex = 0;
				content.style.left = 0 + "px";
				content.style.width = widthrate + "px";

				var img = document.createElement("img");
				img.id = netNode.id + "_img";
				img.src = this.imageUrl + "netImage.PNG";
				img.style.zIndex = 0;
				// img.style.width = netImagewidth+ "px";
				// var widthrate = this.resetImageWidth(this.netLength);
				// img.style.width =widthrate * 1 / this.xyAccurate *
				// this.dashboardWidth * 1+
				// "px";
				img.style.width = widthrate;
				var height = this.resetImageHeight(nWH.h);
				img.style.height = height + "px";

				netNode.style.top = netImageY - height + "px";
				content.appendChild(img);
				netNode.appendChild(content);

				Ext.get(this.mainDragZoneId).dom.appendChild(netNode);
			},
			/**
			 * 创建oStor节点
			 */
			createoStorNode : function(displayXY, oStor, ostorId) {
				var node = document.createElement('div');
				node.id = "oStorNode" + ostorId;
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				node.style.top = displayXY.y + "px";
				node.style.left = displayXY.x + "px";
				if (oStor.cls == "server") {
				}
				if (oStor.cls == null) {
					oStor.cls = 'oStor';
				}
				var nWH = this.cf.deviationObj[oStor.cls]
				var width = this.resetImageWidth(nWH.w);
				node.style.width = width + "px";
				node.style.zIndex = oStor.z;
				node.style.textAlign = 'center';

				var content = document.createElement('div');
				content.style.zIndex = oStor.z;
				content.id = node.id + '_contentDivId';
				content.className = oStor.cls;
				content.style.position = "absolute";
				content.style.bottom = '0px';
				content.style.left = 0 + "px";

				// 节点图片
				var img = document.createElement("img");
				img.id = node.id + "_img";

				var imgtext = gvI18n("oStor Name") + "：" + oStor.title
						+ "    \n" + gvI18n("oStor Service Status") + "："
						+ oStor.nodeStatus.status + "    \n"
						+ gvI18n("Node Capacity") + "："
						+ oStor.nodeStatus.storSize + "GB    \n"
						+ gvI18n("Capacity Used Rate") + "："
						+ oStor.nodeStatus.usedStorSize + "GB   \n"
						+ gvI18n("Data Read Speed") + "："
						+ oStor.nodeStatus.outRate + "MB/s   \n"
						+ gvI18n("Data Write Speed") + "："
						+ oStor.nodeStatus.inRate + "MB/s   \n";

				if (oStor.nodeStatus.cpu != ''
						&& oStor.nodeStatus.cpu != 'null'
						&& oStor.nodeStatus.cpu != null) {
					imgtext = imgtext + gvI18n("CPU Status") + "："
							+ oStor.nodeStatus.cpu + "%    \n";
				}

				if (oStor.nodeStatus.memory != ''
						&& oStor.nodeStatus.memory != 'null'
						&& oStor.nodeStatus.memory != null) {
					imgtext = imgtext + gvI18n("Memory Status") + "："
							+ oStor.nodeStatus.memory + "%    \n";
				}

				if (oStor.nodeStatus.load != ''
						&& oStor.nodeStatus.load != 'null'
						&& oStor.nodeStatus.load != null) {
					imgtext = imgtext + gvI18n("Load Status") + "："
							+ oStor.nodeStatus.load + "    \n";
				}

				img.alt = imgtext;
				img.title = imgtext;

				// img.src = this.imageUrl + oStor.cls + ".png";

				// 取整25/10*10=20
				var imageRate = Math.ceil(oStor.nodeStatus.usageRate / 10) * 10;
				img.src = this.imageUrl + "ostor" + imageRate + ".gif";
				// img.src = this.imageUrl + "ostor" + imageRate + ".gif";
				img.style.zIndex = oStor.z;
				var width = this.resetImageWidth(nWH.w);
				var height = this.resetImageHeight(oStor.height);

				img.style.width = width + "px";
				img.style.height = height + "px";

				var heightrate = this.resetImageHeight(75);
				node.style.height = height + heightrate + "px";
				content.appendChild(img);
				node.appendChild(content);
				var title = document.createElement('div');
				title.id = node.id + '_titleDivId';
				title.innerHTML = oStor.title;
				title.style.zIndex = oStor.z;
				title.style.position = "absolute";
				title.style.bottom = 0 + "px";
				var widthrate = this.resetImageWidth(40);
				title.style.right = (width + widthrate) / 2;

				node.appendChild(title);

				// 存储占用率
				var usrStorRateTitle = document.createElement('div');
				usrStorRateTitle.id = node.id + '_usrStorRateDivId';
				usrStorRateTitle.innerHTML = oStor.nodeStatus.usageRate + "%";
				usrStorRateTitle.style.zIndex = oStor.z;
				usrStorRateTitle.style.position = "absolute";
				usrStorRateTitle.style.bottom = 0 + "px";
				usrStorRateTitle.style.right = 0 + "px";

				node.appendChild(usrStorRateTitle);

				Ext.get(this.mainDragZoneId).dom.appendChild(node);

				// 数据流图片
				this.createINByteSpeed(node.id, oStor);
				this.createOUTByteSpeed(node.id, oStor);

				this.createNetRoot(node.id, oStor);

				// 告警图片
				this.createAlarmGif(node.id, oStor.nodeStatus.status);

			},
			/**
			 * 创建oPara节点
			 */
			createoParaNode : function(displayXY, oPara, type, resId) {
				var node = document.createElement('div');
				node.id = this.prefix + resId;
				node.style.clear = "both";
				node.style.position = "absolute";
				node.className = 'iconZone';
				node.style.top = displayXY.y + "px";
				node.style.left = displayXY.x + "px";
				if (oPara.cls == "server") {
				}
				if (oPara.cls == null) {
					oPara.cls = 'oPara';
				}
				var nWH = this.cf.deviationObj[oPara.cls]
				var width = this.resetImageWidth(nWH.w + 40);
				node.style.width = width + "px";
				node.style.zIndex = oPara.z;

				node.style.textAlign = 'center';

				var status = document.createElement('div');
				status.style.zIndex = oPara.z;
				status.id = node.id + '_statusDivId';
				status.className = oPara.cls;
				status.style.position = "absolute";
				if (type == 'LEFT') {
					status.style.left = 0 + "px";
				} else if (type == 'RIGHT') {
					var width = this.resetImageWidth(40);
					status.style.right = width + "px";
				}

				// oPara节点状态显示
				var localStatus = this.createStatus(
						gvI18n("oPara Node Service Status") + "："
								+ oPara.nodeStatus.selfStatus, 15, oPara.z,
						oPara.nodeStatus.selfStatus, node.id);// 自身状态
				status.appendChild(localStatus);

				var peerStatus = this.createStatus(
						gvI18n("Network NAL Service Status") + "："
								+ oPara.nodeStatus.nalStatus, 35, oPara.z,
						oPara.nodeStatus.nalStatus, node.id);// nal状态
				status.appendChild(peerStatus);

				// var topStatus = this.createStatus("Top", 46, oPara.z,
				// oPara.nodeStatus.oparaTopStatus, node.id);
				// status.appendChild(topStatus);

				// var bottomStatus = this.createStatus("Bottom", 54, oPara.z,
				// oPara.nodeStatus.oparaBottomStatus, node.id);
				// status.appendChild(bottomStatus);

				node.appendChild(status);

				var content = document.createElement('div');
				content.style.zIndex = oPara.z;
				content.id = node.id + '_contentDivId';
				content.className = oPara.cls;
				content.style.position = "absolute";
				if (type == 'LEFT') {
					content.style.right = 0 + "px";
				} else if (type == 'RIGHT') {
					content.style.left = 0 + "px";
				}
				// oPara图片
				var img = document.createElement("img");
				img.id = node.id + "_img";
				img.src = this.imageUrl + oPara.cls + ".png";
				img.style.zIndex = oPara.z;
				var width = this.resetImageWidth(nWH.w);
				img.style.width = width + "px";
				var height = this.resetImageHeight(nWH.h);
				img.style.height = height + "px";

				var imgtext = gvI18n("oPara Name") + "：" + oPara.title
						+ "    \n" + gvI18n("oPara Node Service Status") + "："
						+ oPara.nodeStatus.selfStatus + "    \n"
						+ gvI18n("ha_status") + "：" + oPara.nodeStatus.haStatus
						+ "    \n" + gvI18n("network_ping_status") + "："
						+ oPara.nodeStatus.pingStatus + "    \n"
						+ gvI18n("serial_status") + "："
						+ oPara.nodeStatus.serialStatus + "    \n"
						+ gvI18n("Data Read Speed") + "："
						+ oPara.nodeStatus.outRate + "MB/s   \n"
						+ gvI18n("Data Write Speed") + "："
						+ oPara.nodeStatus.inRate + "MB/s   \n";

				if (oPara.nodeStatus.cpu != ''
						&& oPara.nodeStatus.cpu != 'null'
						&& oPara.nodeStatus.cpu != null) {
					imgtext = imgtext + gvI18n("CPU Status") + "："
							+ oPara.nodeStatus.cpu + "%    \n";
				}

				if (oPara.nodeStatus.memory != ''
						&& oPara.nodeStatus.memory != 'null'
						&& oPara.nodeStatus.memory != null) {
					imgtext = imgtext + gvI18n("Memory Status") + "："
							+ oPara.nodeStatus.memory + "%    \n";
				}

				if (oPara.nodeStatus.load != ''
						&& oPara.nodeStatus.load != 'null'
						&& oPara.nodeStatus.load != null) {
					imgtext = imgtext + gvI18n("Load Status") + "："
							+ oPara.nodeStatus.load + "    \n";
				}

				img.alt = imgtext;
				img.title = imgtext;

				content.appendChild(img);
				node.appendChild(content);
				var title = document.createElement('div');
				title.id = node.id + '_titleDivId';
				title.innerHTML = oPara.title;
				title.style.zIndex = oPara.z;
				title.style.position = "absolute";
				title.style.bottom = 0 + "px";
				var width = this.resetImageWidth(nWH.w + 40);
				title.style.right = (width) / 2;

				node.appendChild(title);
				Ext.get(this.mainDragZoneId).dom.appendChild(node);

			},

			/**
			 * 创建状态对象
			 */
			createStatus : function(statusName, statusPosition, zindex, status,
					id) {
				statusPosition = this.resetImageHeight(statusPosition);
				var localStatus = document.createElement('div');
				localStatus.id = id + "_" + statusName + "StatusDivId";
				localStatus.style.position = "absolute";
				localStatus.style.textAlign = 'left';
				// localStatus.innerHTML = statusName;
				localStatus.style.z = zindex;
				localStatus.style.top = statusPosition + "px";
				localStatus.style.left = 0 + "px";
				var localStatusAlarmGif = document.createElement('img');
				localStatusAlarmGif.id = id + '_localStatusAlarmGifImgId';
				localStatusAlarmGif.style.z = zindex;
				localStatusAlarmGif.style.position = "absolute";
				localStatusAlarmGif.style.top = 0 + "px";
				localStatusAlarmGif.style.left = 0 + "px";

				var strStatus = status;
				if (strStatus != null && strStatus.indexOf("service") < 0) {
					strStatus = "service_" + strStatus.toLowerCase();
				}
				// 若服务状态没有对应的图片，则显示为缺省图片
				var blIsExist = false;
				for (var i = 0; i <this.serviceState.length; i++) {
					if (strStatus != null && strStatus.indexOf(this.serviceState[i]) != -1) {
						blIsExist = true;
						break;
					}
				}
				if(blIsExist == false){
					strStatus = this.defaultServiceState;
				}

				localStatusAlarmGif.src = this.imageUrl + strStatus + ".gif";
				localStatusAlarmGif.alt = statusName;
				localStatusAlarmGif.title = statusName;
				var width = this.resetImageWidth(40);
				localStatusAlarmGif.style.width = width + "px";
				var height = this.resetImageHeight(12);
				localStatusAlarmGif.style.height = height + "px";

				// var localStatusName = document.createElement('div');
				// localStatusName.id = id + "_" + statusName +
				// "StatusNameDivId";
				// localStatusName.style.position = "absolute";
				// localStatusName.style.textAlign = 'left';
				// localStatusName.innerHTML = statusName;
				// localStatusName.style.z = zindex;
				// localStatusName.style.top = 0 + "px";
				// localStatusName.style.left = 0 + "px";
				// var width = this.resetImageWidth(40);
				// localStatusName.style.width = width+"px";
				// var height = this.resetImageHeight(12);
				// localStatusName.style.height = height + "px";

				localStatus.appendChild(localStatusAlarmGif);
				// localStatus.appendChild(localStatusName);

				return localStatus;
			},
			/**
			 * 创建HAoPara组
			 */
			createHAoParasNode : function(oParaLeftDisplayXY, oParaLeft,
					oParaRightDisplayXY, oParaRight, resId) {
				this.createoParaNode(oParaLeftDisplayXY, oParaLeft, "LEFT",
						resId + "_1");
				this.addoParaINandOUTByteSpeed(oParaLeft, oParaLeftDisplayXY);
				this.createoParaNetRoot(oParaLeft, oParaLeftDisplayXY);

				this.createoParaNode(oParaRightDisplayXY, oParaRight, "RIGHT",
						resId + "_2");
				this.addoParaINandOUTByteSpeed(oParaRight, oParaRightDisplayXY);
				this.createoParaNetRoot(oParaRight, oParaRightDisplayXY);
			},

			getTimeStamp : function() {
				return new Date().getTime();
			}

		});
/**
 * 注册xtype
 */
Ext.reg('gv.networktopologydisplaypanel',
		gridview.app.filesystem.webapp.hamonitor.monitor.canvasPanel);
/**
 * //Id数组 var
 * nodeIdArr=['c3001','c3002','doubleel200i1','doubleel200i2','el200i1','el200i2','el200i3','s551','s552','s553','s554','s555','s556','s557','s558','s559','s5510'];
 * //关系资源 var relationData={
 * 'c3001':{relArr:['doubleel200i1','doubleel200i2','el200i1','el200i2','el200i3'],x:
 * 40, y: 2,title:'S4801',cls:'s55'},
 * 'c3002':{relArr:['doubleel200i1','doubleel200i2','el200i1','el200i2','el200i3'],x:
 * 60, y: 2,title:'S4801',cls:'s55'},
 * 'doubleel200i1':{relationArr:['s551','s552'],x: 5,y:
 * 50,title:'E1200i',cls:'del200i','isBox':true},
 * 'doubleel200i2':{relationArr:['s553','s554'],x: 30,y:
 * 50,title:'E1200i',cls:'del200i','isBox':true},
 * 'el200i1':{relArr:['s555','s556'],x: 55, y: 50,title:'C300',cls:'c300'},
 * 'el200i2':{relArr:['s557','s558'],x: 70, y: 50,title:'E600i',cls:'c300'},
 * 'el200i3':{relArr:['s559','s5510'],x: 85, y: 50,title:'E600i',cls:'c300'},
 * 's551':{x: 2, y: 90,title:'s55',cls:'s55'}, 's552':{x: 15, y:
 * 90,title:'s55',cls:'s55'}, 's553':{x: 25, y: 90,title:'s55',cls:'s55'},
 * 's554':{x: 35, y: 90,title:'s55',cls:'s55'}, 's555':{x: 45, y:
 * 90,title:'s55',cls:'s55'}, 's556':{x: 55, y: 90,title:'s55',cls:'s55'},
 * 's557':{x: 65, y: 90,title:'s55',cls:'s55'}, 's558':{x: 73, y:
 * 90,title:'s55',cls:'s55'}, 's559':{x: 82, y: 90,title:'s55',cls:'s55'},
 * 's5510':{x: 90, y: 90,title:'s55',cls:'s55'} };
 */
