; (defn symbol metadata? params body)
; https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/public/lib/core.cljs#L139-L158

(defn crosshair	; 関数名
  {:handles	; handlesオプション
   {:draw (fn	; handleの描画を関数として定義
				; draw関数への引数リストについて -> https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L277-L280
   	[{:params [center x]}]	; crosshair関数への引数がparamsキーワードでvectorで入ってくるのをdirect binding
	    	; ここからdraw関数のbody(というか戻り値）。
			; ここでmapのvectorを返すと、それぞれマウスヒット判定付きのpathとして描かれる（んだと思う）
			; この戻り値がその後どう扱われるかについて
			; -> https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L294-L360
			; 	 https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L41-L83
            [{:id "width" ; idはdrag関数で識別するための名前
              :type "path" ; 描画のタイプ(point|translate|arrow|dia|path)
              :class "dashed" ; 描画のスタイル。ここにあるやつが使えるっぽい(https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L525-L572)
              :path (circle center x)} ; 円を描画
             {:id "center"
              :type "point"
              :pos center} ; 中央に点を表示
             {:id "width" ; idは重複可能。->無理に最初のpathに含める必要はない
              :type "arrow"
              :pos (vec2/+ center [x 0])}])	; 円周上の1点にarrowを表示
    :drag (fn ; handleの操作(マウスドラッグ)時に元の関数(crosshair)の引数がどう変わるかを定義 
			[{:id id ; 操作されている要素のid。draw関数の戻り値に書いたもの
                :pos p ; マウス位置
                :params [c x]}] ; オリジナル（ドラッグ前）の引数。draw関数の:paramsと同じでも良さそうなものだが、スコープが違うので再度書く。
								; やる場面があるかわからないけど、bindingの仕方を変えた方が都合が良い場合は便利
								; drag関数への引数リストについて -> https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L417-L423
			; 戻り値は基本的には引数のparamsと同じ構成にする(ドラッグしてるidを変えさせるchange-idというコマンドもあるっぽい -> [:change-id "width" [p x]])
			; 戻り値の扱い方について -> https://github.com/baku89/glisp/blob/b31bca58966ba4c6e515114e5042a20d7f70382a/src/components/ViewHandles.vue#L427-L462
            (if (= id "center")
              [p x] ; idがcenterだったら第一引数だけ上書き
              [c (vec2/dist c p)]))}} 	; idがcenter以外(この例ではwidthの場合のみ)だったら
			  							; 第二引数をcenterとマウスの距離（つまり新しい半径）に設定し直す
  [center x]
  (transform (translate center)
             (line [(- x) 0] [x 0])
             (line [0 (- x)] [0 x])))