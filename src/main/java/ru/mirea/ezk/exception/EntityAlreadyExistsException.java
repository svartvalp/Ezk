package ru.mirea.ezk.exception;

public class EntityAlreadyExistsException extends BusinessLogicException {

    public EntityAlreadyExistsException(String entityName) {
        super(entityName + " already exists");
    }
}
