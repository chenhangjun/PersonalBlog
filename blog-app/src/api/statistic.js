import request from '@/request'

export function getStatistics() {
  return request({
    url: '/statistic',
    method: 'get',
  })
}
