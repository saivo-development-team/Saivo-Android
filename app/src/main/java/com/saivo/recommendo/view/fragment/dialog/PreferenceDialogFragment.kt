package com.saivo.recommendo.view.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.textWatcher
import kotlinx.android.synthetic.main.fragment_preference_dialog.*

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    PreferenceDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [IPreferenceDialogListener].
 */
class PreferenceDialogFragment : BottomSheetDialogFragment() {
    private var preferenceDialogListener: IPreferenceDialogListener? = null
    private lateinit var categoryText: MaterialTextView
    private lateinit var preferenceLikeText: MaterialTextView
    private lateinit var preferenceDislikeText: MaterialTextView
    private lateinit var preferenceDescriptionText: MaterialTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preference_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        category_auto_complete_textview.apply {
            addTextChangedListener(textWatcher {
                categoryText.text = it
            })
        }
        preference_auto_complete_textview.apply {
            addTextChangedListener(textWatcher {
                it?.apply {
                    if(contains(',')){
                        toString().trim().split(",").apply {
                            preferenceLikeText.text = this[0]
                            if(size > 1 || size < 2) preferenceDislikeText.text = this[1]
                        }
                    }
                }
            })
        }

    }

    private fun initViews(view: View) {
        categoryText = view.findViewById(R.id.preference_category_text)
        preferenceLikeText = view.findViewById(R.id.preference_like_text)
        preferenceDislikeText = view.findViewById(R.id.preference_dislike_text)
        preferenceDescriptionText = view.findViewById(R.id.preference_description_text)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runCatching {
            preferenceDialogListener = context as IPreferenceDialogListener
        }
    }

    override fun onDetach() {
        preferenceDialogListener = null
        super.onDetach()
    }

}
