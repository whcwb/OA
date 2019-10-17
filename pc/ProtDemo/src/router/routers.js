import Main from '@/view/main'

/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面不会缓存
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 * }
 */

export default [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: 'Login - 登录',
      hideInMenu: true
    },
    component: () => import('@/view/login/login.vue')
  },
  {
    path: '/',
    name: 'home',
    redirect: '/home',
    component: Main,
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: '/home',
        name: 'home',
        meta: {
          hideInMenu: true,
          title: '工作台',
          notCache: true
        },
        component: () => import('@/view/single-page/home')
      }
    ]
  },
  {
    path: '/system',
    name: 'system',
    meta: {
      icon: 'logo-buffer',
      title: '系统设置'
    },
    component: Main,
    children: [
      {
        path: 'system-user',
        name: 'system-user',
        meta: {
          icon: 'md-trending-up',
          title: '用户管理'
        },
        component: () => import('@/view/system/system-user')
      },
      {
        path: 'system-role',
        name: 'system-role',
        meta: {
          icon: '_bear',
          title: '角色管理'
        },
        component: () => import('@/view/system/system-root')
      },
      {
        path: 'system-framework',
        name: 'system-framework',
        meta: {
          icon: '_bear',
          title: '机构管理'
        },
        component: () => import('@/view/system/system-framework')
      },
      {
        path: 'system-function',
        name: 'system-function',
        meta: {
          icon: '_bear',
          title: '功能管理'
        },
        component: () => import('@/view/system/system-function')
      },
      {
        path: 'system_Jurisdiction',
        name: 'system_Jurisdiction',
        meta: {
          icon: '_bear',
          title: '功能管理'
        },
        component: () => import('@/view/system/system-Jurisdiction')
      },
      {
        path: 'system-daily',
        name: 'system-daily',
        meta: {
          icon: '_bear',
          title: '日志管理'
        },
        component: () => import('@/view/system/system-daily')
      },
      {
        path: 'dictionaries',
        name: 'dictionaries',
        meta: {
          icon: '_bear',
          title: '字典管理'
        },
        component: () => import('@/view/system/dictionaries')
      },
      {
        path: 'system-ShortMessage',
        name: 'system-ShortMessage',
        meta: {
          icon: '_bear',
          title: '短信管理'
        },
        component: () => import('@/view/system/system-ShortMessage')
      }
    ]
  },
  {
    path: '/basicData',
    name: 'basicData',
    meta: {
      icon: 'logo-buffer',
      title: '基础数据'
    },
    component: Main,
    children: [
      {
        path: 'basicData-charge',
        name: 'basicData-charge',
        meta: {
          icon: 'md-trending-up',
          title: '收费项管理'
        },
        component: () => import('@/view/basicData/basicData-charge')
      },
      {
        path: 'basicData-paymoney',
        name: 'basicData-paymoney',
        meta: {
          icon: 'md-trending-up',
          title: '支出费管理'
        },
        component: () => import('@/view/basicData/basicData-paymoney')
      },
      {
        path: 'basicData-discount',
        name: 'basicData-discount',
        meta: {
          icon: '_bear',
          title: '优惠项管理'
        },
        component: () => import('@/view/basicData/basicData-discount')
      },
      {
        path: 'basicData-groupPurchase',
        name: 'basicData-groupPurchase',
        meta: {
          icon: '_bear',
          title: '团购管理'
        },
        component: () => import('@/view/basicData/basicData-groupPurchase')
      },
      {
        path: 'basicData-archives',
        name: 'basicData-archives',
        meta: {
          icon: '_bear',
          title: '档案柜管理'
        },
        component: () => import('@/view/basicData/basicData-archives')
      },
      // {
      //   path: 'basicData-Coach',
      //   name: 'basicData-Coach',
      //   meta: {
      //     icon: '_bear',
      //     title: '教练员管理'
      //   },
      //   component: () => import('@/view/basicData/basicData-Coach')
      // }
    ]
  },
  {
    path: '/student',
    name: 'student',
    meta: {
      icon: 'logo-buffer',
      title: '学员管理'
    },
    component: Main,
    children: [
      {
        path: 'NotLocalStudent',
        name: 'NotLocalStudent',
        meta: {
          icon: 'md-trending-up',
          title: '其他学员'
        },
        component: () => import('@/view/student/NotLocalStudent')
      },
      {
        path: 'Student-signup',
        name: 'Student-signup',
        meta: {
          icon: 'md-trending-up',
          title: '信息录入'
        },
        component: () => import('@/view/student/Student-signup')
      },
      {
        path: 'Student-teamsignup',
        name: 'Student-teamsignup',
        meta: {
          icon: '_bear',
          title: '团购报名'
        },
        component: () => import('@/view/student/Student-teamsignup')
      },
      {
        path: 'Student-recruit_audit',
        name: 'Student-recruit_audit',
        meta: {
          icon: '_bear',
          title: '报名审核'
        },
        component: () => import('@/view/student/Student-recruit_audit')
      },
      {
        path: 'Student-discount',
        name: 'Student-discount',
        meta: {
          icon: '_bear',
          title: '优惠申请'
        },
        component: () => import('@/view/student/Student-discount')
      },
      // {
      //   path: 'Student-discount_C',
      //   name: 'Student-discount_C',
      //   meta: {
      //     icon: '_bear',
      //     title: '优惠修改'
      //   },
      //   component: () => import('@/view/student/Student-discount_C')
      // },
      // {
      //   path: 'Student-discount_audit',
      //   name: 'Student-discount_audit',
      //   meta: {
      //     icon: '_bear',
      //     title: '优惠审核'
      //   },
      //   component: () => import('@/view/student/Student-discount_audit')
      // },
      {
        path: 'Student-query',
        name: 'Student-query',
        meta: {
          icon: '_bear',
          title: '信息查询'
        },
        component: () => import('@/view/student/Student-query')
      },
      {
        path: 'Student-export',
        name: 'Student-export',
        meta: {
          icon: '_bear',
          title: '学员导出'
        },
        component: () => import('@/view/student/Student-export')
      },
      {
        path: 'Student-Maintain',
        name: 'Student-Maintain',
        meta: {
          icon: '_bear',
          title: '信息维护'
        },
        component: () => import('@/view/student/Student-Maintain')
      },
      {
        path: 'Student-acceptance',
        name: 'Student-acceptance',
        meta: {
          icon: '_bear',
          title: '受理确认'
        },
        component: () => import('@/view/student/Student-acceptance')
      },
      {
        path: 'Student-appointment',
        name: 'Student-appointment',
        meta: {
          icon: '_bear',
          title: '约考导入'
        },
        component: () => import('@/view/student/Student-appointment')
      },
      {
        path: 'Student-examination',
        name: 'Student-examination',
        meta: {
          icon: '_bear',
          title: '考试确认'
        },
        component: () => import('@/view/student/Student-examination')
      },
      {
        path: 'Student-train',
        name: 'Student-train',
        meta: {
          icon: '_bear',
          title: '培训确认'
        },
        component: () => import('@/view/student/Student-train')
      },
      {
        path: 'Student-distribution',
        name: 'Student-distribution',
        meta: {
          icon: '_bear',
          title: '分配教练'
        },
        component: () => import('@/view/student/Student-distribution')
      },
      {
        path: 'Student-Scan',
        name: 'Student-Scan',
        meta: {
          icon: '_bear',
          title: '档案入库'
        },
        component: () => import('@/view/student/Student-Scan')
      },
      {
        path: 'Student-ScanOut',
        name: 'Student-ScanOut',
        meta: {
          icon: '_bear',
          title: '档案出库'
        },
        component: () => import('@/view/student/Student-ScanOut')
      },
      {
        path: 'signOut',
        name: 'signOut',
        meta: {
          icon: '_bear',
          title: '学员退学'
        },
        component: () => import('@/view/student/signOut')
      },
      {
        path: 'Student-archives',
        name: 'Student-archives',
        meta: {
          icon: '_bear',
          title: '电子档案'
        },
        component: () => import('@/view/system/dictionaries')
      },
      {
        path: 'Student-exception',
        name: 'Student-exception',
        meta: {
          icon: 'md-notifications',
          title: '异常查询'
        },
        component: () => import('@/view/student/exception')
      }
    ]
  },
  {
    path: '/charge',
    name: 'charge',
    meta: {
      icon: 'logo-buffer',
      title: '收费管理'
    },
    component: Main,
    children: [
      {
        path: 'Student-discount_audit',
        name: 'Student-discount_audit',
        meta: {
          icon: '_bear',
          title: '优惠审核'
        },
        component: () => import('@/view/student/Student-discount_audit')
      },
      {
        path: 'PaperRecords',
        name: 'PaperRecords',
        meta: {
          icon: 'md-trending-up',
          title: '票据记录'
        },
        component: () => import('@/view/charge/PaperRecords')
      },
      {
        path: 'charge-signUp',
        name: 'charge-signUp',
        meta: {
          icon: 'md-trending-up',
          title: '收费确认'
        },
        component: () => import('@/view/charge/charge-signUp')
      },
      {
        path: 'charge-examination',
        name: 'charge-examination',
        meta: {
          icon: '_bear',
          title: '考试缴费'
        },
        component: () => import('@/view/charge/charge-examination')
      },
      {
        path: 'payment-check',
        name: 'payment-check',
        meta: {
          icon: '_bear',
          title: '缴费对账'
        },
        component: () => import('@/view/charge/payment-check')
      },
      {
        path: 'charge-inspect',
        name: 'charge-inspect',
        meta: {
          icon: '_bear',
          title: '体检收费'
        },
        component: () => import('@/view/charge/charge-inspect')
      },
      {
        path: 'charge-inspect-find',
        name: 'charge-inspect-find',
        meta: {
          icon: '_bear',
          title: '体检查询'
        },
        component: () => import('@/view/charge/charge-inspect-find')
      },
      {
        path: 'charge-inspect-out',
        name: 'charge-inspect-out',
        meta: {
          icon: '_bear',
          title: '体检退费'
        },
        component: () => import('@/view/charge/charge-inspect-out')
      },
      {
        path: 'charge-other',
        name: 'charge-other',
        meta: {
          icon: '_bear',
          title: '其他收费'
        },
        component: () => import('@/view/charge/charge-other')
      },
      {
        path: 'charge-other-pay',
        name: 'charge-other-pay',
        meta: {
          icon: '_bear',
          title: '其他支出'
        },
        component: () => import('@/view/charge/charge-other-pay')
      },
      {
        path: 'charge-examine',
        name: 'charge-examine',
        meta: {
          icon: '_bear',
          title: '收费审核'
        },
        component: () => import('@/view/charge/charge-examine')
      },
      {
        path: 'charge-find',
        name: 'charge-find',
        meta: {
          icon: '_bear',
          title: '收费查询'
        },
        component: () => import('@/view/charge/charge-find')
      },
      {
        path: 'charge-carTyp-B',
        name: 'charge-carTyp-B',
        meta: {
          icon: '_bear',
          title: '变更班型'
        },
        component: () => import('@/view/charge/charge-carTyp-B')
      },
      {
        path: 'charge-carTyp-C',
        name: 'charge-carTyp-C',
        meta: {
          icon: '_bear',
          title: '变更车型'
        },
        component: () => import('@/view/charge/charge-carTyp-C')
      },
      {
        path: 'charge-arrearage',
        name: 'charge-arrearage',
        meta: {
          icon: '_bear',
          title: '分期还款'
        },
        component: () => import('@/view/charge/charge-arrearage')
      },
      {
        path: 'super_Finance',
        name: 'super_Finance',
        meta: {
          icon: '_bear',
          title: '超级财务'
        },
        component: () => import('@/view/charge/super_Finance')
      },
      {
        path: 'charge-bill',
        name: 'charge-bill',
        meta: {
          icon: '_bear',
          title: '票据打印'
        },
        component: () => import('@/view/charge/charge-bill')
      },
      {
        path: 'charge-water',
        name: 'charge-water',
        meta: {
          icon: '_bear',
          title: '流水对账'
        },
        component: () => import('@/view/charge/charge-water')
      }
    ]
  },
  {
    path:'/statistics',
    name:'statistics',
    meta:{
      icon: 'logo-buffer',
      title: '数据统计'
    },
    component: Main,
    children:[
      {
        path: 'statistics-comprehensive',
        name: 'statistics-comprehensive',
        meta: {
          icon: 'md-trending-up',
          title: '学员综合统计'
        },
        component: () => import('@/view/statistics/statistics-comprehensive')
      },
      {
        path: '/statistics-inOut',
        name: 'statistics-inOut',
        meta: {
          icon: 'md-trending-up',
          title: '收支统计'
        },
        component: () => import('@/view/statistics/statistics-inOut')
      },
      {
        path: 'statistics-recruit',
        name: 'statistics-recruit',
        meta: {
          icon: 'md-trending-up',
          title: '今日 招生'
        },
        component: () => import('@/view/statistics/statistics-recruit')
      },
      {
        path: 'statistics-Lrtj',
        name: 'statistics-Lrtj',
        meta: {
          icon: 'md-trending-up',
          title: '招生统计'
        },
        component: () => import('@/view/statistics/statistics-Lrtj')
      },
      {
        path: 'statistics-PassRate',
        name: 'statistics-PassRate',
        meta: {
          icon: 'md-trending-up',
          title: '合格率统计'
        },
        component: () => import('@/view/statistics/PassRate')
      },
      {
        path: 'statistics-Cost',
        name: 'statistics-Cost',
        meta: {
          icon: 'md-trending-up',
          title: '学费统计'
        },
        component: () => import('@/view/statistics/statistics-Cost')
      },
      {
        path: 'statistices-reduce',
        name: 'statistices-reduce',
        meta: {
          icon: 'md-trending-up',
          title: '优惠统计'
        },
        component: () => import('@/view/statistics/statistices-reduce')
      }
    ]
  },
  {
    path:'/jspx',
    name:'jspx',
    meta:{
      icon: 'logo-buffer',
      title: '计时培训'
    },
    component: Main,
    children:[
      {
        path: 'jspx-index',
        name: 'jspx-index',
        meta: {
          icon: 'md-trending-up',
          title: '计时培训'
        },
        component: () => import('@/view/jspx/jspx')
      }
    ]
  },
  {
    path:'/Personnel',
    name:'Personnel',
    meta:{
      icon: 'logo-buffer',
      title: '人事管理'
    },
    component: Main,
    children:[
      {
        path: 'Per_Manage',
        name: 'Per_Manage',
        meta: {
          icon: 'md-trending-up',
          title: '人员档案'
        },
        component: () => import('@/view/Personnel/Per_Manage')
      },
      {
        path: 'basicData-Coach',
        name: 'basicData-Coach',
        meta: {
          icon: '_bear',
          title: '教练员管理'
        },
        component: () => import('@/view/basicData/basicData-Coach')
      },
      {
        path: 'Stock_type',
        name: 'Stock_type',
        meta: {
          icon: 'md-trending-up',
          title: '库存分类'
        },
        component: () => import('@/view/Personnel/Stock_type')
      },
      {
        path: 'Per_Temp',
        name: 'Per_Temp',
        meta: {
          icon: 'md-trending-up',
          title: '库存管理'
        },
        component: () => import('@/view/Personnel/Per_Temp')
      }
    ]
  },
  {
    path:'/clgl',
    name:'clgl',
    meta:{
      icon: 'logo-buffer',
      title: '车辆管理'
    },
    component: Main,
    children:[
      {
        path: 'cllb',
        name: 'cllb',
        meta: {
          icon: 'md-trending-up',
          title: '车辆列表'
        },
        component: () => import('@/view/clgl/cllb')
      },
      {
        path: 'ygxx',
        name: 'ygxx',
        meta: {
          icon: 'md-trending-up',
          title: '运管信息'
        },
        component: () => import('@/view/clgl/yunguan')
      },
      {
        path: 'gqxx',
        name: 'gqxx',
        meta: {
          icon: 'md-trending-up',
          title: '改气信息'
        },
        component: () => import('@/view/clgl/gaiqi')
      },
      {
        path: 'nsBa',
        name: 'nsBa',
        meta: {
          icon: 'md-trending-up',
          title: '年审备案'
        },
        component: () => import('@/view/clgl/nsBa')
      },
      {
        path: 'nsxx',
        name: 'nsxx',
        meta: {
          icon: 'md-trending-up',
          title: '年审信息'
        },
        component: () => import('@/view/clgl/nianshen')
      },
      {
        path: 'bdgl',
        name: 'bdgl',
        meta: {
          icon: 'md-trending-up',
          title: '保单管理'
        },
        component: () => import('@/view/clgl/bdgl')
      },
    ]
  },
  {
    path:'/wx',
    name:'wx',
    meta:{
      icon: 'logo-buffer',
      title: '微信管理'
    },
    component: Main,
    children:[
      {
        path: 'wx-Jurisdiction',
        name: 'wx-Jurisdiction',
        meta: {
          icon: '_bear',
          title: '权限管理'
        },
        component: () => import('@/view/wx/wx_Jurisdiction')
      },{
        path: 'wx-server',
        name: 'wx-server',
        meta: {
          icon: '_bear',
          title: '活动管理'
        },
        component: () => import('@/view/wx/wx_server')
      },{
        path: 'wx-opinion',
        name: 'wx-opinion',
        meta: {
          icon: '_bear',
          title: '意见反馈'
        },
        component: () => import('@/view/wx/wx_opinion')
      },{
        path: 'wx-pl',
        name: 'wx-pl',
        meta: {
          icon: '_bear',
          title: '评论管理'
        },
        component: () => import('@/view/wx/wx_pl')
      },{
        path: 'wx-ts',
        name: 'wx-ts',
        meta: {
          icon: '_bear',
          title: '投诉处理'
        },
        component: () => import('@/view/wx/wx_ts')
      },{
        path: 'wx-yqhy',
        name: 'wx-yqhy',
        meta: {
          icon: '_bear',
          title: '投诉处理'
        },
        component: () => import('@/view/wx/wx_yqhy')
      },{
        path: 'wx-news',
        name: 'wx-news',
        meta: {
          icon: '_bear',
          title: '消息管理'
        },
        component: () => import('@/view/wx/wx_news')
      }
    ]
  },
  {
    path:'/lcsf',
    name:'lcsf',
    meta:{
      icon: 'logo-buffer',
      title: '练车管理'
    },
    component: Main,
    children:[
      {
        path: 'clwh',
        name: 'clwh',
        meta: {
          icon: '_bear',
          title: '考试车维护'
        },
        component: () => import('@/view/lcsf/clgl')
      },
      {
        path: 'lcjl',
        name: 'lcjl',
        meta: {
          icon: '_bear',
          title: '模训记录表'
        },
        component: () => import('@/view/lcsf/lcjl')
      },
        {
            path: 'rebate',
            name: 'rebate',
            meta: {
                icon: '_bear',
                title: '返点确认'
            },
            component: () => import('@/view/charge/rebate')
        },
      {
        path: 'lcsf-statistics',
        name: 'lcsf-statistics',
        meta: {
          icon: '_bear',
          title: '模训统计'
        },
        component: () => import('@/view/lcsf/statistics')
      },
        {
            path: 'lcdjConfig',
            name: 'lcdjConfig',
            meta: {
                icon: '_bear',
                title: '练车费维护'
            },
            component: () => import('@/view/lcsf/lcdjConfig')
        },
      {
        path: 'keerlianche',
        name: 'keerlianche',
        meta: {
          icon: '_bear',
          title: '科目二模训'
        },
        component: () => import('@/view/lcsf/keerlianche')
      },
      {
        path: 'kesanlianche',
        name: 'kesanlianche',
        meta: {
          icon: '_bear',
          title: '科目三模训'
        },
        component: () => import('@/view/lcsf/kesanlianche')
      },
      {
        path: 'aqygl',
        name: 'aqygl',
        meta: {
          icon: '_bear',
          title: '安全员管理'
        },
        component: () => import('@/view/lcsf/aqygl')
      },
      {
        path: 'aqytj',
        name: 'aqytj',
        meta: {
          icon: '_bear',
          title: '安全员统计'
        },
        component: () => import('@/view/lcsf/aqytj')
      },
      {
        path: 'jlytj',
        name: 'jlytj',
        meta: {
          icon: '_bear',
          title: '教练员统计'
        },
        component: () => import('@/view/lcsf/jlytj')
      },
      {
        path: 'jxtj',
        name: 'jxtj',
        meta: {
          icon: '_bear',
          title: '驾校统计'
        },
        component: () => import('@/view/lcsf/jxtj')
      },
      {
        path: 'kmeqd',
        name: 'kmeqd',
        meta: {
          icon: 'ios-create-outline',
          title: '科目二签到'
        },
        component: () => import('@/view/lcsf/kmeqd')
      },
      {
        path: 'kmsqd',
        name: 'kmsqd',
        meta: {
          icon: 'ios-create-outline',
          title: '安全员签到'
        },
        component: () => import('@/view/lcsf/kmsqd')
      },
      {
        path: 'kmetj',
        name: 'kmetj',
        meta: {
          icon: 'ios-create-outline',
          title: '科目二统计'
        },
        component: () => import('@/view/lcsf/kmetj')
      },
        {
            path: 'kmstj',
            name: 'kmstj',
            meta: {
                icon: 'ios-create-outline',
                title: '科目三统计'
            },
            component: () => import('@/view/lcsf/kmstj')
        }
    ]
  },
  // {
  //   path: '',
  //   name: 'doc',
  //   meta: {
  //     title: '文档',
  //     href: 'https://lison16.github.io/iview-admin-doc/#/',
  //     icon: 'ios-book'
  //   }
  // },
  // {
  //   path: '/join',
  //   name: 'join',
  //   component: Main,
  //   children: [
  //     {
  //       path: 'join_page',
  //       name: 'join_page',
  //       meta: {
  //         icon: '_qq',
  //         title: 'QQ群'
  //       },
  //       component: () => import('@/view/join-page.vue')
  //     }
  //   ]
  // },
  // {
  //   path: '/components',
  //   name: 'components',
  //   meta: {
  //     icon: 'logo-buffer',
  //     title: '组件'
  //   },
  //   component: Main,
  //   children: [
  //     {
  //       path: 'count_to_page',
  //       name: 'count_to_page',
  //       meta: {
  //         icon: 'md-trending-up',
  //         title: '数字渐变'
  //       },
  //       component: () => import('@/view/components/count-to/count-to.vue')
  //     },
  //     {
  //       path: 'tables_page',
  //       name: 'tables_page',
  //       meta: {
  //         icon: 'md-grid',
  //         title: '多功能表格'
  //       },
  //       component: () => import('@/view/components/tables/tables.vue')
  //     },
  //     {
  //       path: 'split_pane_page',
  //       name: 'split_pane_page',
  //       meta: {
  //         icon: 'md-pause',
  //         title: '分割窗口'
  //       },
  //       component: () => import('@/view/components/split-pane/split-pane.vue')
  //     },
  //     {
  //       path: 'markdown_page',
  //       name: 'markdown_page',
  //       meta: {
  //         icon: 'logo-markdown',
  //         title: 'Markdown编辑器'
  //       },
  //       component: () => import('@/view/components/markdown/markdown.vue')
  //     },
  //     {
  //       path: 'editor_page',
  //       name: 'editor_page',
  //       meta: {
  //         icon: 'ios-create',
  //         title: '富文本编辑器'
  //       },
  //       component: () => import('@/view/components/editor/editor.vue')
  //     },
  //     {
  //       path: 'icons_page',
  //       name: 'icons_page',
  //       meta: {
  //         icon: '_bear',
  //         title: '自定义图标'
  //       },
  //       component: () => import('@/view/components/icons/icons.vue')
  //     }
  //   ]
  // },
  // {
  //   path: '/update',
  //   name: 'update',
  //   meta: {
  //     icon: 'md-cloud-upload',
  //     title: '数据上传'
  //   },
  //   component: Main,
  //   children: [
  //     {
  //       path: 'update_table_page',
  //       name: 'update_table_page',
  //       meta: {
  //         icon: 'ios-document',
  //         title: '上传Csv'
  //       },
  //       component: () => import('@/view/update/update-table.vue')
  //     },
  //     {
  //       path: 'update_paste_page',
  //       name: 'update_paste_page',
  //       meta: {
  //         icon: 'md-clipboard',
  //         title: '粘贴表格数据'
  //       },
  //       component: () => import('@/view/update/update-paste.vue')
  //     }
  //   ]
  // },
  // {
  //   path: '/directive',
  //   name: 'directive',
  //   meta: {
  //     hide: true
  //   },
  //   component: Main,
  //   children: [
  //     {
  //       path: 'directive_page',
  //       name: 'directive_page',
  //       meta: {
  //         icon: 'ios-navigate',
  //         title: '指令'
  //       },
  //       component: () => import('@/view/directive/directive.vue')
  //     }
  //   ]
  // },
  // {
  //   path: '/multilevel',
  //   name: 'multilevel',
  //   meta: {
  //     icon: 'md-menu',
  //     title: '多级菜单'
  //   },
  //   component: Main,
  //   children: [
  //     {
  //       path: 'level_2_1',
  //       name: 'level_2_1',
  //       meta: {
  //         icon: 'arrow-graph-up-right',
  //         title: '二级-1'
  //       },
  //       component: () => import('@/view/multilevel/level-2-1.vue')
  //     },
  //     {
  //       path: 'level_2_2',
  //       name: 'level_2_2',
  //       meta: {
  //         access: ['super_admin'],
  //         icon: 'arrow-graph-up-right',
  //         showAlways: true,
  //         title: '二级-2'
  //       },
  //       component: parentView,
  //       children: [
  //         {
  //           path: 'level_2_2_1',
  //           name: 'level_2_2_1',
  //           meta: {
  //             icon: 'arrow-graph-up-right',
  //             title: '三级'
  //           },
  //           component: () => import('@/view/multilevel/level-2-2/level-3-1.vue')
  //         }
  //       ]
  //     },
  //     {
  //       path: 'level_2_3',
  //       name: 'level_2_3',
  //       meta: {
  //         icon: 'arrow-graph-up-right',
  //         title: '二级-3'
  //       },
  //       component: () => import('@/view/multilevel/level-2-3.vue')
  //     },
  //   ]
  // },
  {
    path: '/401',
    name: 'error_401',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/401.vue')
  },
  {
    path: '/500',
    name: 'error_500',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/500.vue')
  },
  {
    path: '*',
    name: 'error_404',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/404.vue')
  }
]
