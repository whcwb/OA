export default [
  {
    icon: "md-menu",
    name: "system",
    path: "/system",
    title: "系统管理",
    children: [
      {
        icon: "md-person",
        name: "system-user",
        title: "用户管理",
      }, {
        icon: "ios-people",
        name: "system-role",
        title: "角色管理",
      }, {
        icon: "md-git-network",
        name: "system-framework",
        title: "组织机构",
      }, {
        name: 'system_Jurisdiction',
        icon: ' iconfont icon-quanxian',
        title: '功能管理'
      }, {
        name: 'system-daily',
        icon: ' iconfont icon-rizhi',
        title: '日志管理'
      }, {
        name: 'dictionaries',
        icon: ' iconfont icon-zidian',
        title: '字典管理'
      }, {
        name: 'system-ShortMessage',
        icon: ' iconfont icon-lilun',
        title: '短信管理'
      }
    ]
  },
  {
    name: 'basicData',
    icon: ' iconfont icon-jichuguanli',
    title: '基础数据',
    children: [
      {
        name: 'basicData-charge',
        icon: ' iconfont icon-shoufeiguanli',
        title: '收费项管理'
      }, {
        name: 'basicData-paymoney',
        icon: ' iconfont icon-zhichu',
        title: '支出费管理'
      }, {
        name: 'basicData-discount',
        icon: ' iconfont icon-youhui',
        title: '优惠项管理'
      }, {
      //   name: 'basicData-groupPurchase',
      //   icon: '_bear',
      //   title: '团购管理'
      // },{
        name: 'basicData-archives',
        icon: '_bear',
        title: '档案柜管理'
      },{
        name: 'basicData-Coach',
        icon: ' iconfont icon-jiaolian',
        title: '教练员管理'
      }
    ]
  },
  {
    name: 'student',
    icon: ' iconfont icon-xingming',
    title: '学员管理',
    children: [
      {
        name: 'Student-signup',
        icon: ' iconfont icon-shenhe1',
        title: '招生报名'
      }, {
        name: 'Student-recruit_audit',
        icon: ' iconfont icon-shenhe',
        title: '报名审核'
      },{
        name: 'Student-query',
        icon: ' iconfont icon-ccgl-dizhichaxun-6 ',
        title: '学员查询'
      }, {
        name: 'Student-acceptance',
        icon: ' iconfont icon-icon1 ',
        title: '受理确认'
      }, {
        name: 'Student-appointment',
        icon: ' iconfont icon-bp-',
        title: '预约确认'
      }, {
        name: 'Student-examination',
        icon: ' iconfont icon-daoru',
        title: '考试确认'
      },{
        name: 'Student-train',
        icon: ' iconfont icon-peixun',
        title: '培训确认'
      },{
        name: 'Student-distribution',
        icon: ' iconfont icon-ZHicon-',
        title: '分配教练'
      },{
        name: 'Student-Scan',
        icon: ' iconfont icon-saoma',
        title: '档案入库'
      },{
        name: 'Student-ScanOut',
        icon: ' iconfont icon-saoma',
        title: '档案出库'
      },{
        name: 'signOut',
        icon: ' iconfont icon-yituixue',
        title: '学员退学'
      },
    ]
  },
  {
    name: 'charge',
    icon: ' iconfont icon-shoufeiguanli',
    title: '收费管理',
    children: [
      {
        name: 'Student-discount_audit',
        icon: ' iconfont icon-shenhe ',
        title: '优惠审核'
      }, {
        name: 'charge-signUp',
        icon: ' iconfont icon-baomingjiaofei',
        title: '报名收费'
      }, {
        name: 'charge-examination',
        icon: ' iconfont icon-kaoshi',
        title: '考试缴费'
      }, {
        name: 'charge-inspect',
        icon: ' iconfont icon-tijianjiaofei',
        title: '体检收费'
      },{
        name: 'charge-inspect-find',
        icon: ' iconfont icon-tijianjiaofei',
        title: '体检查询'
      },{
        name: 'charge-other',
        icon: ' iconfont icon-qita',
        title: '其他收费'
      },{
        name: 'charge-other-pay',
        icon: ' iconfont icon-qita',
        title: '其他支出'
      },{
        name: 'charge-examine',
        icon: ' iconfont icon-shenhe2',
        title: '收费审核'
      },{
      //   name: 'charge-bill',
      //   icon: '_bear',
      //   title: '票据打印'
      // },{
        name: 'charge-find',
        icon: ' iconfont icon-chaxun',
        title: '收费查询'
      // },{
      //   name: 'charge-water',
      //   icon: '_bear',
      //   title: '流水对账'
      },
      {
        name: 'charge-carTyp-C',
        icon: ' iconfont icon-baomingjiaofei',
        title: '变更车型'
      },
      {
        name: 'charge-arrearage',
        icon: ' iconfont icon-fenqi',
        title: '分期还款'
      }
    ]
  },
  {
    name: 'statistics',
    icon: ' iconfont icon-jichuguanli',
    title: '数据统计',
    children: [
      {
        name: 'statistics-comprehensive',
        icon: ' iconfont icon-shoufeiguanli',
        title: '综合统计'
      }, {
        name: 'statistics-inOut',
        icon: ' iconfont icon-zhichu',
        title: '收支统计'
      }, {
        name: 'statistics-recruit',
        icon: ' iconfont icon-youhui',
        title: '招生统计'
      },
      {
        name: 'statistics-Cost',
        icon: ' iconfont icon-shoufeiguanli',
        title: '学费统计'
      },
    ]
  },
  {
    name: 'wx',
    icon: ' iconfont icon-jichuguanli',
    title: '微信管理',
    children: [
      {
        name: 'wx-server',
        icon: 'md-trending-up',
        title: '活动管理'
      },{
        name: 'wx-opinion',
        icon: 'md-trending-up',
        title: '意见反馈'
      },{
        name: 'wx-pl',
        icon: 'md-trending-up',
        title: '评论管理'
      },{
        name: 'wx-ts',
        icon: 'md-trending-up',
        title: '投诉管理'
      },{
        name: 'wx-yqhy',
        icon: 'md-trending-up',
        title: '好友邀请'
      }
    ]
  },
]
