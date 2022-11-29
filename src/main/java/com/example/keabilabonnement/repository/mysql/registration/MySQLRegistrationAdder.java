package com.example.keabilabonnement.repository.mysql.registration;

import com.example.keabilabonnement.contracts.db.DbStatement;
import com.example.keabilabonnement.models.registration.RentalAgreement;
import com.example.keabilabonnement.services.db.DbPreparedStatement;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Service
public class MySQLRegistrationAdder {
    public MySQLRegistrationAdder() {
    }

    public Boolean add(RentalAgreement agreement) {
        try {
            var prepared = DbPreparedStatement.get(statement());
            initValues(prepared,agreement).execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private String statement(){
        return """
                insert into RentalAgreement (StartDate, 
                                            ExpirationDate, 
                                            DeliveryDate, 
                                            Id, CarNumber, 
                                            CustomerLicense_Id)
                values (?,?,?,?,?,?);
                """;
    }

    private PreparedStatement initValues(PreparedStatement statement, RentalAgreement agreement) throws SQLException {
        statement.setDate(1,toDate(agreement.getStart()));
        statement.setDate(2,toDate(agreement.getExpiration()));
        statement.setDate(3,toDate(agreement.getDelevery()));
        statement.setString(4,agreement.getId());
        statement.setString(5,agreement.getCar().getCarNumber());
        statement.setString(6,agreement.getCustomer().getLicenseID());
        return statement;
    }

    private Date toDate(LocalDate date){
        return Date.valueOf(date);
    }
}
