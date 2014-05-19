/**
 * 拓扑 画布Panel 配置
 */
informationcollectorNetworktopologyCanvasPanelConfig = {
    /**
     * 画线点修正对象
     */
    deviationObj: {
        //每一种className对应一组数据XY是修正数据，WH是图片的width,height
        server: {
            x: 20,
            y: 20,
            w: 40,
            h: 40
        }, //40*40
        modem: {
            x: 20,
            y: 25,
            w: 40,
            h: 40
        },  //40*40
        c300: {
            x: 80,
            y: 75,
            w: 161,
            h: 154
        }, //161*154
        s55: {
            x: 40,
            y: 20,
            w: 87,
            h: 38
        }, //87*38
        el: {
            x: 40,
            y: 65,
            w: 83,
            h: 134
        },   //83*134
        //双冗余
        el_r: {
            x: 90,
            y: 64,
            w: 185,
            h: 129
        }, //185*129
        pen_32:{
        	x: 5,
            y: 25,
            w: 32,
            h: 32
        },
        rubber_32:{
        	x: 16,
            y: 23,
            w: 32,
            h: 32
        },
        oPara: {
        	x: 20,
            y: 20,
            w: 169,
            h: 70
        },
        oStor: {
        	x: 20,
            y: 20,
            w: 84,
            h: 50
        },
        net: {
        	x: 240,
            y: 20,
            w: 1440,
            h: 25
        },
        netroot: {
        	x: 20,
            y: 20,
            w: 20,
            h: 35
        }
    }
}