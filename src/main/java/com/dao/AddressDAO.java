package com.dao;

import com.model.Address;
import java.util.List;

/**
 * Data Access Object interface for Address operations.
 */
public interface AddressDAO {

    /** Add a new address */
    int addAddress(Address address);

    /** Update an address */
    int updateAddress(Address address);

    /** Delete an address */
    int deleteAddress(int addressId);

    /** Get addresses by user ID */
    List<Address> getAddressesByUserId(int userId);

    /** Get default address for user */
    Address getDefaultAddress(int userId);

    /** Set an address as default */
    int setDefaultAddress(int userId, int addressId);

    /** Get address by ID */
    Address getAddressById(int id);
}
