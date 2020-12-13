import axios from 'axios'

export function getTableList(param) {
  return axios({
    url: 'http://127.0.0.1:8099/index/1/10',
    method: 'get',
    param
  })
}

export function excuteShell(param) {
  return axios({
    url: 'http://127.0.0.1:8099/execute',
    method: 'post',
    param
  })
}
