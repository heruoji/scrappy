# Scrappy

## Webクローリング、WebスクレイピングのJava製フレームワーク

某フレームワークScrapy的なものをJavaで再現しようとしたものです。

その結果、似ても似つかぬScrapyのスクラップ版(Scrappy!)ができました。

## 利用方法
1. プロジェクトをクローン
2. src/main/java/com/example/spider/implディレクトリにクロールしたい媒体用のSpider実装クラスを追加
3. com/example/core/itempipeline/implディレクトリに必要に応じてItemPipeline実装クラスを追加
4. com/example/settingsディレクトリにクロールしたい媒体用の設定ファイルを追加（今はItemPipelineの設定のみ）
5. com/example/core/Engine.javaを実行。以下引数
   * n：実行するSpiderクラスのname属性
   * p：ページの表示にplaywrightを使うかどうか
   * s：利用する設定ファイルのSettingsアノテーションのname属性 

   例：-n rikunabiNext -p -s rikunabiNext
