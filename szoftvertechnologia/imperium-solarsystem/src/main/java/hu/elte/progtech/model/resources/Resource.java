package hu.elte.progtech.model.resources;

import hu.elte.progtech.exceptions.ResourceTypeNotMatchException;
import lombok.Getter;

public class Resource {
    @Getter
    private int quantity;
    @Getter
    private ResourceType resourceType;

    public Resource(int quantity, ResourceType resourceType) {
        this.quantity = quantity;
        this.resourceType = resourceType;
    }

    public Resource(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.quantity = 0;
    }

    public void add(Resource resource) {
        if (resource.resourceType == this.resourceType) {
            this.quantity += resource.quantity;
        } else {
            throw new ResourceTypeNotMatchException("Try to add " + resource.resourceType + " to resource " + resourceType);
        }
    }

    public void add(int quantity) {
        this.quantity += quantity;
    }

    public boolean remove(Resource resource) {
        var newQuantity = this.quantity - resource.quantity;
        if (newQuantity < 0) {
            return false;
        } else if (resource.resourceType == this.resourceType) {
            this.quantity = newQuantity;
        } else {
            throw new ResourceTypeNotMatchException("Try to remove " + resource.resourceType + " to resource " + resourceType);
        }
        return true;
    }

    public boolean remove(int quantity) {
        var newQuantity = this.quantity - quantity;
        if (newQuantity < 0) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }

}
