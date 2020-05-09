package com.example.softwaretest;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.googlecode.openwnn.legacy.CLOUDSONG.*;
import com.googlecode.openwnn.legacy.OnHandWritingRecognize;
import com.googlecode.openwnn.legacy.WnnWord;
import com.googlecode.openwnn.legacy.handwritingboard.HandWritingBoardLayout;
import com.googlecode.openwnn.legacy.listener.IKeyboardSelectListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MySoftWare extends RelativeLayout implements OnCandidateSelected, OnHandWritingRecognize, OnPinyinQueryed, OnClickListener {

	private Context context;
	private LinearLayout keyboardLetter;
	private HandWritingBoardLayout handWritingBoard;
	private TextView inputShow;
	// private LinearLayout container;
	private RelativeLayout candidateContainer;
	private TextView KEY_DEL, btnSelectKeyboard, btnSelectHandWriting, btnCleanHandWriting;
	private TextView PinYinInput;
	private CandidateView mCandidateView;
	private StringBuilder currentInput = new StringBuilder();
	private CloudKeyboardInputManager ckManager;
	private IKeyboardSelectListener keyboardSelectListener;
	private View view;
	private boolean isSelectEn = true;

	public void setView(View view) {
		this.view = view;
	}

	public void setKeyboardSelectListener(IKeyboardSelectListener keyboardSelectListener) {
		this.keyboardSelectListener = keyboardSelectListener;
	}

	public MySoftWare(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView_Method();
	}

	public void initView_Method() {
		LayoutInflater.from(context).inflate(R.layout.input_view, this);
		findViewById();

		ckManager = new CloudKeyboardInputManager();
		ckManager.setOnPinyinQueryed(this);

		mCandidateView = new CandidateView(context);
		mCandidateView.setOnCandidateSelected(this);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
		lp1.addRule(CENTER_VERTICAL);
		lp1.width = ViewGroup.LayoutParams.MATCH_PARENT;
		candidateContainer.addView(mCandidateView, lp1);

		keyboardLetter.setVisibility(View.VISIBLE);
		handWritingBoard.setVisibility(View.INVISIBLE);
		handWritingBoard.setOnHandWritingRecognize(this);
	}

	private void processInput(char[] chars) {
		ckManager.processInput(chars);
	}

	private void clearCurrentInput() {
		currentInput.delete(0, currentInput.length());
	}

	@Override
	public void onClick(View view) {

		if (view instanceof Button && !isSelectEn && !isNumeric(((Button) view).getText().toString())) {
			keyboardSelectListener.keyboardSelect(this.view, ((Button) view).getText().toString());
			return;
		}

		switch (view.getId()) {
			case R.id.KEY_0:
				keyboardSelectListener.keyboardSelect(this.view, "0");
				break;
			case R.id.KEY_1:
				keyboardSelectListener.keyboardSelect(this.view, "1");
				break;
			case R.id.KEY_2:
				keyboardSelectListener.keyboardSelect(this.view, "2");
				break;
			case R.id.KEY_3:
				keyboardSelectListener.keyboardSelect(this.view, "3");
				break;
			case R.id.KEY_4:
				keyboardSelectListener.keyboardSelect(this.view, "4");
				break;
			case R.id.KEY_5:
				keyboardSelectListener.keyboardSelect(this.view, "5");
				break;
			case R.id.KEY_6:
				keyboardSelectListener.keyboardSelect(this.view, "6");
				break;
			case R.id.KEY_7:
				keyboardSelectListener.keyboardSelect(this.view, "7");
				break;
			case R.id.KEY_8:
				keyboardSelectListener.keyboardSelect(this.view, "8");
				break;
			case R.id.KEY_9:
				keyboardSelectListener.keyboardSelect(this.view, "9");
				break;
			case R.id.KEY_A:
				processInputA();
				break;
			case R.id.KEY_B:
				processInputB();

				break;
			case R.id.KEY_C:
				processInputC();
				break;
			case R.id.KEY_D:
				processInputD();
				break;
			case R.id.KEY_E:
				processInputE();
				break;
			case R.id.KEY_F:
				processInputF();
				break;
			case R.id.KEY_G:
				processInputG();
				break;
			case R.id.KEY_H:
				processInputH();
				break;
			case R.id.KEY_I:
				processInputI();
				break;
			case R.id.KEY_J:
				processInputJ();
				break;
			case R.id.KEY_K:
				processInputK();
				break;
			case R.id.KEY_L:
				processInputL();
				break;
			case R.id.KEY_M:
				processInputM();
				break;
			case R.id.KEY_N:
				processInputN();
				break;
			case R.id.KEY_O:
				processInputO();
				break;
			case R.id.KEY_P:
				processInputP();
				break;
			case R.id.KEY_Q:
				processInputQ();
				break;
			case R.id.KEY_R:
				processInputR();
				break;
			case R.id.KEY_S:
				processInputS();
				break;
			case R.id.KEY_T:
				processInputT();
				break;
			case R.id.KEY_U:
				processInputU();
				break;
			case R.id.KEY_V:
				processInputV();
				break;
			case R.id.KEY_W:
				processInputW();
				break;
			case R.id.KEY_X:
				processInputX();
				break;
			case R.id.KEY_Y:
				processInputY();
				break;
			case R.id.KEY_Z:
				processInputZ();
				break;
			case R.id.KEY_DEL:
				// 只有当没有输入的时候才能删除当前的文字
				if (PinYinInput.getText().length() == 0) {
					keyboardSelectListener.deleteText(this.view);
				}
				delCurrentInput();
				// 当没有拼音了的时候，清空 CandidateView
				if (PinYinInput.getText().length() == 0)
					mCandidateView.clear();
				break;
			case R.id.selecthandwriting:
				btnCleanHandWriting.setVisibility(VISIBLE);
				showHandWriting();
				break;
			case R.id.selectkeyboard:
				if (isSelectEn) {
					SpannableString spannableString = new SpannableString("中/英");
					RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.5f);
					spannableString.setSpan(relativeSizeSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					btnSelectKeyboard.setText(spannableString);
				} else {
					SpannableString spannableString = new SpannableString("中/英");
					RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.5f);
					spannableString.setSpan(relativeSizeSpan, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					btnSelectKeyboard.setText(spannableString);
				}
				isSelectEn = !isSelectEn;

				btnCleanHandWriting.setVisibility(GONE);
				showKeyboard();
				break;
			case R.id.clean:
				resetHandWritingRecognizeClicked();
				break;
		}
	}

	void delCurrentInput() {
		if (isHandWriting()) {
			delCurrentInputNoPinyin();
		} else {
			if (PinYinInput.getText().length() > 0) {
				ckManager.processDel();
			} else {
				delCurrentInputNoPinyin();
				mCandidateView.clear();
			}
		}
	}

	private void delCurrentInputNoPinyin() {
		if (currentInput.length() > 0) {
			currentInput.deleteCharAt(currentInput.length() - 1);
		}
		inputShow.setText(currentInput.toString());
	}

	void showHandWriting() {
		handWritingBoard.setVisibility(View.VISIBLE);
		keyboardLetter.setVisibility(View.INVISIBLE);
		ckManager.delAll();
		PinYinInput.setText("");
		mCandidateView.clear();
	}

	void showKeyboard() {
		handWritingBoard.setVisibility(View.GONE);
		keyboardLetter.setVisibility(View.VISIBLE);
		resetHandWritingRecognize();
		mCandidateView.clear();
	}

	void resetHandWritingRecognizeClicked() {
		resetHandWritingRecognize();
		mCandidateView.clear();
	}

	@Override
	public void candidateSelected(WnnWord wnnWord) {
		String candidate = null;
		if (wnnWord != null) {
			candidate = wnnWord.candidate;
		}
		if (!TextUtils.isEmpty(candidate)) {
			cleanCurrentPinyinView();
			appendCandidate(candidate);
			inputShow.setText(currentInput.toString());
			keyboardSelectListener.keyboardSelect(view, candidate);
		}
		mCandidateView.clear();
		if (isHandWriting()) {
			resetHandWritingRecognize();
		} else {
			ckManager.candidateSelected(wnnWord);
		}
	}

	private void cleanCurrentPinyinView() {
		PinYinInput.setText("");
	}

	private void appendCandidate(String candidate) {
		currentInput.append(candidate);
		// currentIndex += candidate.length();
	}

	@Override
	public void handWritingRecognized(ArrayList<WnnWord> result) {
		mCandidateView.setSuggestions(result, false, false);
	}

	private void resetHandWritingRecognize() {
		handWritingBoard.reset_recognize();
	}

	/*
	 * 删除和上屏都要区分手写和字母输入；
	 */
	private boolean isHandWriting() {
		return handWritingBoard.getVisibility() == View.VISIBLE;
	}

	@Override
	public void onPinyinQueryed(PinyinQueryResult pyQueryResult) {
		if (pyQueryResult != null) {
			mCandidateView.setSuggestions(pyQueryResult.getCandidateList(), false, false);
			String pinyin = pyQueryResult.getCurrentInput();
			updatePinyin(pinyin);
		}
	}

	private void updatePinyin(String pinyin) {
		PinYinInput.setText(pinyin);
	}

	private void findViewById() {
		findViewById(R.id.KEY_A).setOnClickListener(this);
		findViewById(R.id.KEY_B).setOnClickListener(this);
		findViewById(R.id.KEY_C).setOnClickListener(this);
		findViewById(R.id.KEY_D).setOnClickListener(this);
		findViewById(R.id.KEY_E).setOnClickListener(this);
		findViewById(R.id.KEY_F).setOnClickListener(this);
		findViewById(R.id.KEY_G).setOnClickListener(this);
		findViewById(R.id.KEY_H).setOnClickListener(this);
		findViewById(R.id.KEY_I).setOnClickListener(this);
		findViewById(R.id.KEY_J).setOnClickListener(this);
		findViewById(R.id.KEY_K).setOnClickListener(this);
		findViewById(R.id.KEY_L).setOnClickListener(this);
		findViewById(R.id.KEY_M).setOnClickListener(this);
		findViewById(R.id.KEY_N).setOnClickListener(this);
		findViewById(R.id.KEY_O).setOnClickListener(this);
		findViewById(R.id.KEY_P).setOnClickListener(this);
		findViewById(R.id.KEY_Q).setOnClickListener(this);
		findViewById(R.id.KEY_R).setOnClickListener(this);
		findViewById(R.id.KEY_S).setOnClickListener(this);
		findViewById(R.id.KEY_T).setOnClickListener(this);
		findViewById(R.id.KEY_U).setOnClickListener(this);
		findViewById(R.id.KEY_V).setOnClickListener(this);
		findViewById(R.id.KEY_W).setOnClickListener(this);
		findViewById(R.id.KEY_X).setOnClickListener(this);
		findViewById(R.id.KEY_Y).setOnClickListener(this);
		findViewById(R.id.KEY_Z).setOnClickListener(this);
		findViewById(R.id.KEY_0).setOnClickListener(this);
		findViewById(R.id.KEY_1).setOnClickListener(this);
		findViewById(R.id.KEY_2).setOnClickListener(this);
		findViewById(R.id.KEY_3).setOnClickListener(this);
		findViewById(R.id.KEY_4).setOnClickListener(this);
		findViewById(R.id.KEY_5).setOnClickListener(this);
		findViewById(R.id.KEY_6).setOnClickListener(this);
		findViewById(R.id.KEY_7).setOnClickListener(this);
		findViewById(R.id.KEY_8).setOnClickListener(this);
		findViewById(R.id.KEY_9).setOnClickListener(this);

		KEY_DEL = findViewById(R.id.KEY_DEL);
		KEY_DEL.setOnClickListener(this);

		keyboardLetter = findViewById(R.id.keyboard_letter);
		candidateContainer = findViewById(R.id.candidateContainer);
		handWritingBoard = findViewById(R.id.handwrtingboard);

		btnSelectHandWriting = findViewById(R.id.selecthandwriting);
		btnSelectHandWriting.setOnClickListener(this);

		btnSelectKeyboard = findViewById(R.id.selectkeyboard);
		btnSelectKeyboard.setOnClickListener(this);
		SpannableString spannableString = new SpannableString("中/英");
		RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.5f);
		spannableString.setSpan(relativeSizeSpan, 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		btnSelectKeyboard.setText(spannableString);

		btnCleanHandWriting = findViewById(R.id.clean);
		btnCleanHandWriting.setOnClickListener(this);

		PinYinInput = findViewById(R.id.pinyinInput);
		inputShow = findViewById(R.id.candidateselected);
	}

	void processInputA() {
		processInput(new char[]{'a'});
	}

	void processInputB() {
		processInput(new char[]{'b'});
	}

	void processInputC() {
		processInput(new char[]{'c'});
	}

	void processInputD() {
		processInput(new char[]{'d'});
	}

	void processInputE() {
		processInput(new char[]{'e'});
	}

	void processInputF() {
		processInput(new char[]{'f'});
	}

	void processInputG() {
		processInput(new char[]{'g'});
	}

	void processInputH() {
		processInput(new char[]{'h'});
	}

	void processInputI() {
		processInput(new char[]{'i'});
	}

	void processInputJ() {
		processInput(new char[]{'j'});
	}

	void processInputK() {
		processInput(new char[]{'k'});
	}

	void processInputL() {
		processInput(new char[]{'l'});
	}

	void processInputM() {
		processInput(new char[]{'m'});
	}

	void processInputN() {
		processInput(new char[]{'n'});
	}

	void processInputO() {
		processInput(new char[]{'o'});
	}

	void processInputP() {
		processInput(new char[]{'p'});
	}

	void processInputQ() {
		processInput(new char[]{'q'});
	}

	void processInputR() {
		processInput(new char[]{'r'});
	}

	void processInputS() {
		processInput(new char[]{'s'});
	}

	void processInputT() {
		processInput(new char[]{'t'});
	}

	void processInputU() {
		processInput(new char[]{'u'});
	}

	void processInputV() {
		processInput(new char[]{'v'});
	}

	void processInputW() {
		processInput(new char[]{'w'});
	}

	void processInputX() {
		processInput(new char[]{'x'});
	}

	void processInputY() {
		processInput(new char[]{'y'});
	}

	void processInputZ() {
		processInput(new char[]{'z'});
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
