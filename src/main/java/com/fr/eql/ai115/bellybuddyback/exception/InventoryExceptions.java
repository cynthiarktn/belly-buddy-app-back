package com.fr.eql.ai115.bellybuddyback.exception;

public class InventoryExceptions {

  public static class InventoryItemNotFoundException extends RuntimeException {
    public InventoryItemNotFoundException(Long id) {
      super("Inventory item not found with id: " + id);
    }
  }

  public static class InventoryItemAlreadyExistsException extends RuntimeException {
    public InventoryItemAlreadyExistsException(Long id) {
      super("Inventory item already exists with id: " + id);
    }
  }
}
