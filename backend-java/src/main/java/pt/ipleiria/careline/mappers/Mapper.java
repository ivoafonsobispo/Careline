package pt.ipleiria.careline.mappers;

public interface Mapper<A, B> {
    B mapToDTO(A a);

    A mapFrom(B b);
}
