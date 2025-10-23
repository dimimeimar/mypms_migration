package com.mypms.antikatavoles.dao;

import com.mypms.config.DatabaseConfig;
import com.mypms.antikatavoles.model.Antikatavoli;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO κλάση για την Αντικαταβολή - Database Operations
 */
public class AntikatavoliDAO {

    private final Connection connection;

    public AntikatavoliDAO() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }

    /**
     * Δημιουργία νέας αντικαταβολής
     */
    public boolean create(Antikatavoli antikatavoli) {
        String sql = """
            INSERT INTO antikatavoli (id_apostolis, apodothike, imerominia_apodosis,
                                      id_apodosis, parastatikoACS, parastatikoMyPMS, sxolia)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, antikatavoli.getIdApostolis());
            stmt.setBoolean(2, antikatavoli.getApodothike());
            stmt.setDate(3, antikatavoli.getImerominiaApodosis() != null ?
                    Date.valueOf(antikatavoli.getImerominiaApodosis()) : null);
            stmt.setObject(4, antikatavoli.getIdApodosis() > 0 ? antikatavoli.getIdApodosis() : null);
            stmt.setString(5, antikatavoli.getParastatikoACS());
            stmt.setString(6, antikatavoli.getParastatikoMyPMS());
            stmt.setString(7, antikatavoli.getSxolia());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    antikatavoli.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα δημιουργίας αντικαταβολής: " + e.getMessage());
        }
        return false;
    }

    /**
     * Ανάκτηση όλων των αντικαταβολών
     */
    public List<Antikatavoli> findAll() {
        List<Antikatavoli> list = new ArrayList<>();
        String sql = "SELECT * FROM antikatavoli ORDER BY created_at DESC LIMIT 1000";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToAntikatavoli(rs));
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα ανάκτησης αντικαταβολών: " + e.getMessage());
        }
        return list;
    }

    /**
     * Ανάκτηση αντικαταβολής βάσει ID
     */
    public Antikatavoli findById(int id) {
        String sql = "SELECT * FROM antikatavoli WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAntikatavoli(rs);
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα ανάκτησης αντικαταβολής: " + e.getMessage());
        }
        return null;
    }

    /**
     * Ενημέρωση αντικαταβολής
     */
    public boolean update(Antikatavoli antikatavoli) {
        String sql = """
            UPDATE antikatavoli SET
                id_apostolis = ?, apodothike = ?, imerominia_apodosis = ?,
                id_apodosis = ?, parastatikoACS = ?, parastatikoMyPMS = ?, sxolia = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, antikatavoli.getIdApostolis());
            stmt.setBoolean(2, antikatavoli.getApodothike());
            stmt.setDate(3, antikatavoli.getImerominiaApodosis() != null ?
                    Date.valueOf(antikatavoli.getImerominiaApodosis()) : null);
            stmt.setObject(4, antikatavoli.getIdApodosis() > 0 ? antikatavoli.getIdApodosis() : null);
            stmt.setString(5, antikatavoli.getParastatikoACS());
            stmt.setString(6, antikatavoli.getParastatikoMyPMS());
            stmt.setString(7, antikatavoli.getSxolia());
            stmt.setInt(8, antikatavoli.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Σφάλμα ενημέρωσης αντικαταβολής: " + e.getMessage());
            return false;
        }
    }

    /**
     * Διαγραφή αντικαταβολής
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM antikatavoli WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Σφάλμα διαγραφής αντικαταβολής: " + e.getMessage());
            return false;
        }
    }

    /**
     * Μετατροπή ResultSet σε Antikatavoli object
     */
    private Antikatavoli mapResultSetToAntikatavoli(ResultSet rs) throws SQLException {
        Antikatavoli antikatavoli = new Antikatavoli();

        antikatavoli.setId(rs.getInt("id"));
        antikatavoli.setIdApostolis(rs.getInt("id_apostolis"));
        antikatavoli.setApodothike(rs.getBoolean("apodothike"));

        Date dateApodosis = rs.getDate("imerominia_apodosis");
        if (dateApodosis != null) {
            antikatavoli.setImerominiaApodosis(dateApodosis.toLocalDate());
        }

        Integer idApodosis = (Integer) rs.getObject("id_apodosis");
        if (idApodosis != null) {
            antikatavoli.setIdApodosis(idApodosis);
        }

        antikatavoli.setParastatikoACS(rs.getString("parastatikoACS"));
        antikatavoli.setParastatikoMyPMS(rs.getString("parastatikoMyPMS"));
        antikatavoli.setSxolia(rs.getString("sxolia"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            antikatavoli.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            antikatavoli.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return antikatavoli;
    }
}
