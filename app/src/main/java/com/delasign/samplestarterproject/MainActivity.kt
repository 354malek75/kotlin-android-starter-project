import android.hardware.camera2.*
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import java.io.File

class StealthCameraRecorder(private val context: android.content.Context) {

    private var mediaRecorder: MediaRecorder? = null
    private val outputFile = File(context.externalCacheDir, "stealth_capture.mp4")

    fun startRecording() {
        // إعداد الـ MediaRecorder للتسجيل
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.CAMERA)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setOutputFile(outputFile.absolutePath)
            setVideoSize(640, 480) // دقة منخفضة لسرعة المعالجة
            setVideoFrameRate(30)
            prepare()
            start()
        }

        // جدولة الإيقاف بعد 3 ثوانٍ
        Handler(Looper.getMainLooper()).postDelayed({
            stopRecording()
        }, 3000)
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}
