package ru.mirea.ezk.exception;

public class EntityNotFoundException extends BusinessLogicException {
    public EntityNotFoundException(String entityName) {
        super(entityName + " not found");
    }
}
