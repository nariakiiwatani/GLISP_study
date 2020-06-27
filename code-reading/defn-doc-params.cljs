; (defn symbol metadata? params body)
; https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/public/lib/core.cljs#L139-L158

(defn my-func ; 関数名
	{:doc "Just for explaining about defn" ; ドキュメントとインスペクタに表示されるテキスト
		:params [	; ドキュメントとインスペクタのためのパラメータリスト。
					; 指定できるキーワードはここで、(https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ParamControl.vue#L135-L140)
					; それぞれの役割は
					; :type,:label -> (https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ParamControl.vue#L11-L80)を参照
					; :constraint (min|max|step) -> (https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ParamControl.vue#L352-L375)を参照
					; :default -> プログラムから引数が与えられなかった時にインスペクタに表示する値。
					;			あくまでインスペクタの話であって、関数呼び出し時のデフォルト引数の役割ではない。'Error: parameter 'hoge' is not specified'のエラーはこれでは回避できない。
					; :key, :keys -> 後述
					; :desc -> ドキュメント表示用説明？
	   		{:label "first" :type "number"}
            {:label "second" :type "number"}
            &	; & を置くとその後ろがoptionalな引数の定義になる
				; optionalな引数には2パターンあって、どちらかを選択する
			; Aパターン
			; {:label "rest" :type "number"} ; 可変長引数のパターン。インスペクタで増減可能
			; Bパターン
			{:keys [	; キーワード付き引数のパターンをvectorで
				{:key :optional-flag-1 :label "Flag1" :type "number"}	; :keyで受け付けるkeywordを指定。インスペクタのラベルは:labelで上書き可能
				{:key :optional-flag-2 :type "number"}	; :labelを指定しない場合、:keyの後のキーワードを記号区切りの語頭大文字（この場合はOptional Flag 2）にしたものになる
			]}
		]
		:unit "cool"	; インスペクタ表示用の単位文字列
		:returns {:type "number"}	; ドキュメント表示用の戻り値型
	}
  [v1 v2 & flags]	; bodyで使う引数名定義。
  (+ v1 v2)	; 戻り値
)