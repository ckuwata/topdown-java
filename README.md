# topdown-java

Javaで実装された、宣言的で組み合わせ可能なトップダウンパーサー（パーサーコンビネータ）ライブラリです。

## 概要

`topdown-java` は、小さなパーサーを組み合わせて複雑な構文解析器を構築するためのフレームワークを提供します。
BNFのような形式で文法を定義するように、パーサーを宣言的かつ直感的に記述することができます。

## 主な特徴

- **宣言的なAPI**: `Choice`, `Many`, `ChainLeft` のようなコンビネータを使い、解析ルールを簡潔に表現できます。
- **組み合わせ可能**: 文字列、正規表現などの基本的なパーサーを組み合わせて、より複雑なパーサーを構築できます。
- **Javaベース**: 使い慣れたJavaの言語機能やエコシステムの上で動作します。
- **柔軟なエラー処理**: 解析結果は `Result<T>` オブジェクトで返され、成功と失敗を明確にハンドリングできます。

## 使い方

このライブラリの基本的な構成要素は `Parser<T>` インターフェースです。このインターフェースは `parse` メソッドを一つだけ持つ関数型インターフェースです。

```java
@FunctionalInterface
public interface Parser<T>  {
    public Result<T> parse(CharacterSequence source);
}
```

`CharacterSequence` は解析対象の入力文字列を表し、`Result<T>` は解析結果（成功した場合は値、失敗した場合はエラー情報）を保持します。

### 使用例：四則演算の電卓

以下は、このライブラリを使って簡単な電卓（`1+2*3`のような式を計算する）を実装する例です。

`pom.xml` や `build.gradle.kts` にライブラリを追加してください。（注: このライブラリはまだ公開リポジトリには登録されていません）

式の定義は、加減算、乗除算、そして数値や括弧といった要素（因子）から構成されます。

- `expr` = `term` { (`+`|`-`) `term` }
- `term` = `factor` { (`*`|`/`) `factor` }
- `factor` = `number` | `(` `expr` `)`

これを `topdown-java` を使って表現すると以下のようになります。

```java
// 式全体 (加減算)
class ExprParser implements Parser<Expr> {
    @Override
    public Result<Expr> parse(CharacterSequence source) {
        // TermParserを左結合の演算子(AddSubParser)で連結する
        return new ChainLeft<Expr>(new TermParser(), new AddSubParser()).parse(source);
    }
}

// 項 (乗除算)
class TermParser implements Parser<Expr> {
    @Override
    public Result<Expr> parse(CharacterSequence source) {
        // FactorParserを左結合の演算子(MulDivParser)で連結する
        return new ChainLeft<Expr>(new FactorParser(), new MulDivParser()).parse(source);
    }
}

// 因子 (数値 or 括弧)
class FactorParser implements Parser<Expr> {
    @Override
    public Result<Expr> parse(CharacterSequence source) {
        // まず数値として解析を試みる
        var n = new NumberParser().parse(source);
        if (n.failed()) {
            // 数値でなければ、括弧で囲まれた式として解析する
            return new Str("(").parse(source).then(o -> {
                return new ExprParser().parse(source).then(ret -> {
                    return new Str(")").parse(source).then(c -> Result.success(ret));
                });
            });
        }
        return n;
    }
}
```

より詳細な実装は `example/` ディレクトリの `Calculator.java` を参照してください。

## ビルド方法

このプロジェクトはGradleでビルドされています。

```bash
# プロジェクト全体をビルド
./gradlew build

# ライブラリのみをビルド
./gradlew :lib:build

# サンプルを実行
# (実行可能なmainクラスをexampleに実装する必要があります)
```

## ライセンス

このプロジェクトは [MIT License](LICENCE) の下で公開されています。
