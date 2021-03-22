package com.dev777popov.convertimgapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dev777popov.convertimgapp.R
import com.dev777popov.convertimgapp.databinding.ActivityMainBinding
import com.dev777popov.convertimgapp.mvp.model.Image
import com.dev777popov.convertimgapp.mvp.presenter.MainPresenter
import com.dev777popov.convertimgapp.mvp.view.MainView
import com.dev777popov.convertimgapp.ui.converter.ImageConverter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null

    private val presenter by moxyPresenter {
        MainPresenter(AndroidSchedulers.mainThread(), ImageConverter(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)

        vb?.btnConvert?.setOnClickListener {
            presenter.convertClick()
        }
    }

    override fun selectImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(Intent.createChooser(intent, getString(R.string.title_select_img)), SELECT_IMG_REQ_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMG_REQ_ID) {
            if (resultCode == RESULT_OK) {
                data?.data?.let { uri ->
                    val bytes =
                        contentResolver?.openInputStream(uri)?.buffered()?.use { it.readBytes() }
                    bytes?.let {
                        presenter.imageSelected(Image(it))
                    }
                }
            }
        }
    }

    private var convertDialog: AlertDialog? = null
    override fun showProgressInConvert() {
        convertDialog = AlertDialog.Builder(this)
            .setMessage(R.string.text_dialog_message)
            .setNegativeButton(R.string.btn_cancel_dialog){ _, _ -> presenter.convertCancel()}
            .create()
        convertDialog?.show()
    }

    override fun hideProgressInConvert() {
        convertDialog?.dismiss()
    }

    override fun showSuccessConvert() {
        Toast.makeText(this, getString(R.string.toast_success_convert), Toast.LENGTH_SHORT).show()
    }

    override fun showCancelConvert() {
        Toast.makeText(this, getString(R.string.toast_cancel_convert), Toast.LENGTH_SHORT).show()
    }

    override fun showErrorConvert() {
        Toast.makeText(this, getString(R.string.toast_error_convert), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SELECT_IMG_REQ_ID = 1007
    }
}