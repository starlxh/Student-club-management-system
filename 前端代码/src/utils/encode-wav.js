export function encodeWAVFromFloat32(float32Array, sampleRate = 16000) {
  const buffer = float32To16BitPCM(float32Array)
  const wavBuffer = createWAVHeader(buffer.length, sampleRate)
  const blob = new Blob([wavBuffer, buffer], { type: 'audio/wav' })
  return blob
}

function float32To16BitPCM(float32Array) {
  const len = float32Array.length
  const int16Array = new Int16Array(len)

  for (let i = 0; i < len; i++) {
    const s = Math.max(-1, Math.min(1, float32Array[i]))
    int16Array[i] = s < 0 ? s * 0x8000 : s * 0x7FFF
  }

  return new DataView(int16Array.buffer)
}

function createWAVHeader(dataLength, sampleRate) {
  const buffer = new ArrayBuffer(44)
  const view = new DataView(buffer)

  const channels = 1
  const bitsPerSample = 16
  const byteRate = sampleRate * channels * bitsPerSample / 8
  const blockAlign = channels * bitsPerSample / 8

  let offset = 0
  const writeString = (str) => {
    for (let i = 0; i < str.length; i++) {
      view.setUint8(offset++, str.charCodeAt(i))
    }
  }

  writeString('RIFF')
  view.setUint32(offset, 36 + dataLength * 2, true); offset += 4
  writeString('WAVE')
  writeString('fmt ')
  view.setUint32(offset, 16, true); offset += 4
  view.setUint16(offset, 1, true); offset += 2
  view.setUint16(offset, channels, true); offset += 2
  view.setUint32(offset, sampleRate, true); offset += 4
  view.setUint32(offset, byteRate, true); offset += 4
  view.setUint16(offset, blockAlign, true); offset += 2
  view.setUint16(offset, bitsPerSample, true); offset += 2
  writeString('data')
  view.setUint32(offset, dataLength * 2, true); offset += 4

  return view
}
