import request from '@/utils/request'

export function queryAllClubList() {
  return request.get('club/queryAllClubList')
}
