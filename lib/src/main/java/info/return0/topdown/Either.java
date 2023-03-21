package info.return0.topdown;

public abstract class Either<L, R> {

    private Either() {
    }

    public static <L, R> Either<L, R> left(L leftValue) {
        return new Left<>(leftValue);
    }

    public static <L, R> Either<L, R> right(R rightValue) {
        return new Right<>(rightValue);
    }

    public abstract boolean isLeft();

    public abstract boolean isRight();

    public abstract L getLeft();

    public abstract R getRight();

    public static final class Left<L, R> extends Either<L, R> {
        private final L value;

        private Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public L getLeft() {
            return value;
        }

        @Override
        public R getRight() {
            throw new UnsupportedOperationException("Cannot getRight() on a Left.");
        }
    }

    public static final class Right<L, R> extends Either<L, R> {
        private final R value;

        private Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public L getLeft() {
            throw new UnsupportedOperationException("Cannot getLeft() on a Right.");
        }

        @Override
        public R getRight() {
            return value;
        }
    }
}
