import request from '@/utils/request'

export function getMeetingInfoById(miId) {
  return request.get('meeting/getMeetingInfoById', {
    params: {
      miId: miId
    }
  })
}

export function getMeetInfoListByUserId() {
  return request.get('meeting/getMeetInfoListByUserId')
}

export function getMeetingInfoByName(name) {
  return request.get('meeting/getMeetingInfoByName', {
    params: {
      name: name
    }
  })
}

export function getJitsiToken(infos, isModerator) {
  return request.post('meeting/token', {
    room: infos.name,
    startTime: infos.startTime,
    endTime: infos.endTime,
    isModerator: isModerator
  })
}

export function uploadAudio(formData) {
  return request.post('meeting/audio', formData)
}

export function addMeetingInfo(formData) {
  return request.post('meeting/addMeetingInfo', formData)
}

export function queryMeetingInfoList(listQuery) {
  return request.get('meeting/queryMeetingInfoList', {
    params: listQuery
  })
}

export function editMeetingInfo(formData) {
  return request.post('meeting/editMeetingInfo', formData)
}

export function deleteMeetingById(miId) {
  return request.delete('meeting/deleteById', {
    params: {
      miId: miId
    }
  })
}

