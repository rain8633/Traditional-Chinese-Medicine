// 匹配规则
const rules = {
  username: [
    { required: true, message: '姓名不能为空',trigger: 'blur' }
  ],
  password: [
    { required: true, message: '密码不能为空',trigger: 'blur' }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号码',trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  location: [
    { required: true,message: '请输入地区', trigger: 'change' }
  ],
  hname: [
    { required: true,message: '请输入所在医院', trigger: 'change' }
  ],
   keshi: [
    { required: true,message: '请输入所属科室', trigger: 'change' }
  ]
}

// 地区选择
const cities = [{
  label: '北京',
  value: '北京'
}, {
  label: '天津',
  value: '天津'
}, {
  label: '河北',
  value: '河北'
}, {
  label: '山西',
  value: '山西'
}, {
  label: '内蒙古',
  value: '内蒙古'
}, {
  label: '辽宁',
  value: '辽宁'
}, {
  label: '吉林',
  value: '吉林'
}, {
  label: '黑龙江',
  value: '黑龙江'
}, {
  label: '上海',
  value: '上海'
}, {
  label: '江苏',
  value: '江苏'
}, {
  label: '浙江',
  value: '浙江'
}, {
  label: '安徽',
  value: '安徽'
}, {
  label: '福建',
  value: '福建'
}, {
  label: '江西',
  value: '江西'
}, {
  label: '山东',
  value: '山东'
}, {
  label: '河南',
  value: '河南'
}, {
  label: '湖北',
  value: '湖北'
}, {
  label: '湖南',
  value: '湖南'
}, {
  label: '广东',
  value: '广东'
}, {
  label: '广西',
  value: '广西'
}, {
  label: '海南',
  value: '海南'
}, {
  label: '重庆',
  value: '重庆'
}, {
  label: '四川',
  value: '四川'
}, {
  label: '贵州',
  value: '贵州'
}, {
  label: '云南',
  value: '云南'
}, {
  label: '西藏',
  value: '西藏'
}, {
  label: '陕西',
  value: '陕西'
}, {
  label: '甘肃',
  value: '甘肃'
}, {
  label: '青海',
  value: '青海'
}, {
  label: '宁夏',
  value: '宁夏'
}, {
  label: '新疆',
  value: '新疆'
}, {
  label: '香港',
  value: '香港'
}, {
  label: '澳门',
  value: '澳门'
}, {
  label: '台湾',
  value: '台湾'
}]

export {
  rules,
  cities
}
