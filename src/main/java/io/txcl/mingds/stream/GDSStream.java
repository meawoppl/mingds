package io.txcl.mingds.stream;

import com.google.common.collect.Streams;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * This class is mostly automatically generated. I was getting really tired of typing
 *
 * <pre>{@code Stream<GDSIIRecord<?>>}</pre>
 *
 * over and over
 */
public class GDSStream implements Stream<GDSIIRecord<?>> {
    private final Stream<GDSIIRecord<?>> recordStream;

    public GDSStream(Stream<GDSIIRecord<?>> recordStream) {
        this.recordStream = recordStream;
    }

    public static GDSStream of(Stream<GDSIIRecord<?>> recordStream) {
        return new GDSStream(recordStream);
    }

    public static GDSStream of(GDSIIRecord<?>... records) {
        return of(Stream.of(records));
    }

    public static GDSStream of(Stream<GDSIIRecord<?>>... streams) {
        return of(Streams.concat(streams));
    }

    public static GDSStream of(List<GDSIIRecord<?>> records) {
        return of(records.stream());
    }

    public static GDSStream of(Iterator<GDSIIRecord<?>> recordIterator) {
        return of(Streams.stream(recordIterator));
    }

    public static GDSStream from(Path path) throws IOException {
        return of(GDSIterator.fromPath(path));
    }

    public static GDSStream empty() {
        return of(Stream.empty());
    }

    public static GDSStream from(byte[] bytes) {
        return of(GDSIterator.fromBytes(bytes));
    }

    public static void to(Path path, GDSStream recordStream) throws IOException {
        OutputStream fos = new FileOutputStream(path.toFile());
        recordStream.forEach(
                r -> {
                    try {
                        fos.write(r.serialize());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static byte[] serialize(GDSStream recordBaseStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        recordBaseStream.forEach(
                r -> {
                    try {
                        baos.write(r.serialize());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        return baos.toByteArray();
    }

    public GDSStream concat(GDSStream... others) {
        Stream<GDSIIRecord<?>> concatenated = this.recordStream;
        for (int i = 0; i < others.length; i++) {
            concatenated = Streams.concat(concatenated, others[i]);
        }
        return GDSStream.of(concatenated);
    }

    public GDSStream concat(List<GDSStream> others) {
        Stream<GDSIIRecord<?>> concatenated = this.recordStream;
        for (int i = 0; i < others.size(); i++) {
            concatenated = Streams.concat(concatenated, others.get(i));
        }
        return GDSStream.of(concatenated);
    }

    public GDSStream concat(GDSIIRecord<?>... records) {
        return concat(GDSStream.of(records));
    }

    public GDSStream concat(GDSStream records) {
        return GDSStream.of(Streams.concat(this.recordStream, records.recordStream));
    }

    public GDSStream concat(Stream<GDSIIRecord<?>> records) {
        return concat(GDSStream.of(records));
    }

    // NOTE(meawoppl) everything below is basically a ugly wrapper on Stream to make it act just
    // correctly implement that interface. Its a bit yucky, but all just boneheaded pass-through

    @Override
    public Stream<GDSIIRecord<?>> filter(Predicate<? super GDSIIRecord<?>> predicate) {
        return this.recordStream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super GDSIIRecord<?>, ? extends R> function) {
        return this.recordStream.map(function);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super GDSIIRecord<?>> toIntFunction) {
        return this.recordStream.mapToInt(toIntFunction);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super GDSIIRecord<?>> toLongFunction) {
        return this.recordStream.mapToLong(toLongFunction);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super GDSIIRecord<?>> toDoubleFunction) {
        return this.recordStream.mapToDouble(toDoubleFunction);
    }

    @Override
    public <R> Stream<R> flatMap(
            Function<? super GDSIIRecord<?>, ? extends Stream<? extends R>> function) {
        return this.recordStream.flatMap(function);
    }

    @Override
    public IntStream flatMapToInt(Function<? super GDSIIRecord<?>, ? extends IntStream> function) {
        return this.recordStream.flatMapToInt(function);
    }

    @Override
    public LongStream flatMapToLong(
            Function<? super GDSIIRecord<?>, ? extends LongStream> function) {
        return this.recordStream.flatMapToLong(function);
    }

    @Override
    public DoubleStream flatMapToDouble(
            Function<? super GDSIIRecord<?>, ? extends DoubleStream> function) {
        return this.recordStream.flatMapToDouble(function);
    }

    @Override
    public Stream<GDSIIRecord<?>> distinct() {
        return this.recordStream.distinct();
    }

    @Override
    public Stream<GDSIIRecord<?>> sorted() {
        return this.recordStream.sorted();
    }

    @Override
    public Stream<GDSIIRecord<?>> sorted(Comparator<? super GDSIIRecord<?>> comparator) {
        return this.recordStream.sorted(comparator);
    }

    @Override
    public Stream<GDSIIRecord<?>> peek(Consumer<? super GDSIIRecord<?>> consumer) {
        return this.recordStream.peek(consumer);
    }

    @Override
    public Stream<GDSIIRecord<?>> limit(long l) {
        return this.recordStream.limit(l);
    }

    @Override
    public Stream<GDSIIRecord<?>> skip(long l) {
        return this.recordStream.skip(l);
    }

    @Override
    public void forEach(Consumer<? super GDSIIRecord<?>> consumer) {
        this.recordStream.forEach(consumer);
    }

    @Override
    public void forEachOrdered(Consumer<? super GDSIIRecord<?>> consumer) {
        this.recordStream.forEachOrdered(consumer);
    }

    @Override
    public Object[] toArray() {
        return this.recordStream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> intFunction) {
        return this.recordStream.toArray(intFunction);
    }

    @Override
    public GDSIIRecord<?> reduce(
            GDSIIRecord<?> gdsiiRecord, BinaryOperator<GDSIIRecord<?>> binaryOperator) {
        return this.recordStream.reduce(gdsiiRecord, binaryOperator);
    }

    @Override
    public Optional<GDSIIRecord<?>> reduce(BinaryOperator<GDSIIRecord<?>> binaryOperator) {
        return this.recordStream.reduce(binaryOperator);
    }

    @Override
    public <U> U reduce(
            U u,
            BiFunction<U, ? super GDSIIRecord<?>, U> biFunction,
            BinaryOperator<U> binaryOperator) {
        return this.recordStream.reduce(u, biFunction, binaryOperator);
    }

    @Override
    public <R> R collect(
            Supplier<R> supplier,
            BiConsumer<R, ? super GDSIIRecord<?>> biConsumer,
            BiConsumer<R, R> biConsumer1) {
        throw new RuntimeException("Not implemented...");
    }

    @Override
    public <R, A> R collect(Collector<? super GDSIIRecord<?>, A, R> collector) {
        return this.recordStream.collect(collector);
    }

    @Override
    public Optional<GDSIIRecord<?>> min(Comparator<? super GDSIIRecord<?>> comparator) {
        return this.recordStream.min(comparator);
    }

    @Override
    public Optional<GDSIIRecord<?>> max(Comparator<? super GDSIIRecord<?>> comparator) {
        return this.recordStream.max(comparator);
    }

    @Override
    public long count() {
        return this.recordStream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super GDSIIRecord<?>> predicate) {
        return this.recordStream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super GDSIIRecord<?>> predicate) {
        return this.recordStream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super GDSIIRecord<?>> predicate) {
        return this.recordStream.noneMatch(predicate);
    }

    @Override
    public Optional<GDSIIRecord<?>> findFirst() {
        return this.recordStream.findFirst();
    }

    @Override
    public Optional<GDSIIRecord<?>> findAny() {
        return this.recordStream.findAny();
    }

    @Override
    public Iterator<GDSIIRecord<?>> iterator() {
        return this.recordStream.iterator();
    }

    @Override
    public Spliterator<GDSIIRecord<?>> spliterator() {
        return this.recordStream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return this.recordStream.isParallel();
    }

    @Override
    public Stream<GDSIIRecord<?>> sequential() {
        return this.recordStream.sequential();
    }

    @Override
    public Stream<GDSIIRecord<?>> parallel() {
        return this.recordStream.parallel();
    }

    @Override
    public Stream<GDSIIRecord<?>> unordered() {
        return this.recordStream.unordered();
    }

    @Override
    public Stream<GDSIIRecord<?>> onClose(Runnable runnable) {
        return this.recordStream.onClose(runnable);
    }

    @Override
    public void close() {
        this.recordStream.close();
    }
}
