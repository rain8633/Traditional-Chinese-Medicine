// export function MP(ak) {
//   return new Promise(function (resolve, reject) {
//     window.onload = function () {
//       resolve(BMap)
//     }
//     var script = document.createElement("script");
//     script.type = "text/javascript";
//     script.src = "http://api.map.baidu.com/api?v=2.0&ak="+ak+"&callback=init";
//     script.onerror = reject;
//     document.head.appendChild(script);
//   })
// }
export default function loadBMap(ak) {
  return new Promise(function(resolve, reject) {
    if (typeof window.BMap !== 'undefined') {
      resolve(window.BMap)
      return true
    }
    window.onBMapCallback = function() {
      resolve(window.BMap)
    }
    let script = document.createElement('script')
    script.type = 'text/javascript'
    script.src =
      'http://api.map.baidu.com/api?v=3.0&ak=' + ak + '&callback=onBMapCallback'
    script.onerror = reject
    document.head.appendChild(script)
  })
}
