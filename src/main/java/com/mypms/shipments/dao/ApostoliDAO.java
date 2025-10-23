package com.mypms.shipments.dao;

import com.mypms.config.DatabaseConfig;
import com.mypms.shipments.model.Apostoli;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO κλάση για την Αποστολή - Database Operations
 */
public class ApostoliDAO {

    private final Connection connection;

    public ApostoliDAO() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }

    /**
     * Δημιουργία νέας αποστολής
     */
    public boolean create(Apostoli apostoli) {
        String sql = """
        INSERT INTO apostoles (
              kodikos_pelati, courier, arithmos_apostolis, arithmos_paraggelias,
              imerominia_paralabis, imerominia_ekdosis, imerominia_anaxorisis, antikatavoli,
              paraliptis, xora, poli, diefthinsi, tk_paralipti, tilefono_stathero, tilefono_kinito,
              istoriko, status_apostolis, status_details, sxolia, status_mypms, imerominia_paradosis,
              delivery_flag, returned_flag, non_delivery_reason_code, shipment_status, delivery_info, status_locked
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, apostoli.getKodikosPelati());
            stmt.setString(2, apostoli.getCourier());
            stmt.setString(3, apostoli.getArithmosApostolis());
            stmt.setString(4, apostoli.getArithmosParaggelias());
            stmt.setDate(5, apostoli.getImerominiaParalabis() != null ? Date.valueOf(apostoli.getImerominiaParalabis()) : null);
            stmt.setDate(6, apostoli.getImerominiaEkdosis() != null ? Date.valueOf(apostoli.getImerominiaEkdosis()) : null);
            stmt.setDate(7, apostoli.getImerominiaAnaxorisis() != null ? Date.valueOf(apostoli.getImerominiaAnaxorisis()) : null);
            stmt.setBigDecimal(8, apostoli.getAntikatavoli());
            stmt.setString(9, apostoli.getParaliptis());
            stmt.setString(10, apostoli.getXora());
            stmt.setString(11, apostoli.getPoli());
            stmt.setString(12, apostoli.getDiefthinsi());
            stmt.setString(13, apostoli.getTkParalipti());
            stmt.setString(14, apostoli.getTilefonoStathero());
            stmt.setString(15, apostoli.getTilefonoKinito());
            stmt.setString(16, apostoli.getIstoriko());
            stmt.setString(17, apostoli.getStatusApostolis());
            stmt.setString(18, apostoli.getStatusDetails());
            stmt.setString(19, apostoli.getSxolia());
            stmt.setString(20, apostoli.getStatusMypms());
            stmt.setDate(21, apostoli.getImerominiaParadosis() != null ? Date.valueOf(apostoli.getImerominiaParadosis()) : null);
            stmt.setInt(22, apostoli.getDeliveryFlag() != null ? apostoli.getDeliveryFlag() : 0);
            stmt.setInt(23, apostoli.getReturnedFlag() != null ? apostoli.getReturnedFlag() : 0);
            stmt.setString(24, apostoli.getNonDeliveryReasonCode());
            stmt.setObject(25, apostoli.getShipmentStatus());
            stmt.setString(26, apostoli.getDeliveryInfo());
            stmt.setBoolean(27, apostoli.getStatusLocked() != null ? apostoli.getStatusLocked() : false);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    apostoli.setIdApostolis(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα δημιουργίας αποστολής: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ανάκτηση αποστολής βάσει ID
     */
    public Apostoli findById(int idApostolis) {
        String sql = "SELECT * FROM apostoles WHERE id_apostolis = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idApostolis);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToApostoli(rs);
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα ανάκτησης αποστολής: " + e.getMessage());
        }
        return null;
    }

    /**
     * Ανάκτηση όλων των αποστολών
     */
    public List<Apostoli> findAll() {
        List<Apostoli> apostolesList = new ArrayList<>();
        String sql = "SELECT * FROM apostoles ORDER BY imerominia_paralabis DESC, created_at DESC LIMIT 1000";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                apostolesList.add(mapResultSetToApostoli(rs));
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα ανάκτησης αποστολών: " + e.getMessage());
        }
        return apostolesList;
    }

    /**
     * Ενημέρωση αποστολής
     */
    public boolean update(Apostoli apostoli) {
        String sql = """
        UPDATE apostoles SET
            kodikos_pelati = ?, courier = ?, arithmos_apostolis = ?,
            arithmos_paraggelias = ?, imerominia_paralabis = ?, imerominia_anaxorisis = ?,
            antikatavoli = ?, paraliptis = ?, xora = ?, poli = ?,
            diefthinsi = ?, tk_paralipti = ?, tilefono_stathero = ?, tilefono_kinito = ?,
            istoriko = ?, status_apostolis = ?, status_details = ?, sxolia = ?, status_mypms = ?,
            imerominia_paradosis = ?, delivery_flag = ?, returned_flag = ?,
            non_delivery_reason_code = ?, shipment_status = ?, delivery_info = ?,
            status_locked = ?
        WHERE id_apostolis = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, apostoli.getKodikosPelati());
            stmt.setString(2, apostoli.getCourier());
            stmt.setString(3, apostoli.getArithmosApostolis());
            stmt.setString(4, apostoli.getArithmosParaggelias());
            stmt.setDate(5, apostoli.getImerominiaParalabis() != null ? Date.valueOf(apostoli.getImerominiaParalabis()) : null);
            stmt.setDate(6, apostoli.getImerominiaAnaxorisis() != null ? Date.valueOf(apostoli.getImerominiaAnaxorisis()) : null);
            stmt.setBigDecimal(7, apostoli.getAntikatavoli());
            stmt.setString(8, apostoli.getParaliptis());
            stmt.setString(9, apostoli.getXora());
            stmt.setString(10, apostoli.getPoli());
            stmt.setString(11, apostoli.getDiefthinsi());
            stmt.setString(12, apostoli.getTkParalipti());
            stmt.setString(13, apostoli.getTilefonoStathero());
            stmt.setString(14, apostoli.getTilefonoKinito());
            stmt.setString(15, apostoli.getIstoriko());
            stmt.setString(16, apostoli.getStatusApostolis());
            stmt.setString(17, apostoli.getStatusDetails());
            stmt.setString(18, apostoli.getSxolia());
            stmt.setString(19, apostoli.getStatusMypms());
            stmt.setDate(20, apostoli.getImerominiaParadosis() != null ? Date.valueOf(apostoli.getImerominiaParadosis()) : null);
            stmt.setInt(21, apostoli.getDeliveryFlag() != null ? apostoli.getDeliveryFlag() : 0);
            stmt.setInt(22, apostoli.getReturnedFlag() != null ? apostoli.getReturnedFlag() : 0);
            stmt.setString(23, apostoli.getNonDeliveryReasonCode());
            stmt.setObject(24, apostoli.getShipmentStatus());
            stmt.setString(25, apostoli.getDeliveryInfo());
            stmt.setBoolean(26, apostoli.getStatusLocked() != null ? apostoli.getStatusLocked() : false);
            stmt.setInt(27, apostoli.getIdApostolis());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Σφάλμα ενημέρωσης αποστολής: " + e.getMessage());
            return false;
        }
    }

    /**
     * Διαγραφή αποστολής
     */
    public boolean delete(int idApostolis) {
        String sql = "DELETE FROM apostoles WHERE id_apostolis = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idApostolis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Σφάλμα διαγραφής αποστολής: " + e.getMessage());
            return false;
        }
    }

    /**
     * Αναζήτηση αποστολών με φίλτρα
     */
    public List<Apostoli> search(String searchTerm, String courier, String statusMypms) {
        List<Apostoli> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM apostoles WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (arithmos_apostolis LIKE ? OR paraliptis LIKE ? OR poli LIKE ?)");
            String searchPattern = "%" + searchTerm.trim() + "%";
            parameters.add(searchPattern);
            parameters.add(searchPattern);
            parameters.add(searchPattern);
        }

        if (courier != null && !courier.trim().isEmpty() && !courier.equals("όλοι")) {
            sql.append(" AND courier = ?");
            parameters.add(courier);
        }

        if (statusMypms != null && !statusMypms.trim().isEmpty() && !statusMypms.equals("όλα")) {
            sql.append(" AND status_mypms = ?");
            parameters.add(statusMypms);
        }

        sql.append(" ORDER BY imerominia_paralabis DESC LIMIT 1000");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(mapResultSetToApostoli(rs));
            }
        } catch (SQLException e) {
            System.err.println("Σφάλμα αναζήτησης αποστολών: " + e.getMessage());
        }
        return results;
    }

    /**
     * Μετατροπή ResultSet σε Apostoli object
     */
    private Apostoli mapResultSetToApostoli(ResultSet rs) throws SQLException {
        Apostoli apostoli = new Apostoli();

        apostoli.setIdApostolis(rs.getInt("id_apostolis"));
        apostoli.setKodikosPelati(rs.getString("kodikos_pelati"));
        apostoli.setCourier(rs.getString("courier"));
        apostoli.setArithmosApostolis(rs.getString("arithmos_apostolis"));
        apostoli.setArithmosParaggelias(rs.getString("arithmos_paraggelias"));

        Date dateParalabis = rs.getDate("imerominia_paralabis");
        if (dateParalabis != null) apostoli.setImerominiaParalabis(dateParalabis.toLocalDate());

        Date dateParadosis = rs.getDate("imerominia_paradosis");
        if (dateParadosis != null) apostoli.setImerominiaParadosis(dateParadosis.toLocalDate());

        Date dateEkdosis = rs.getDate("imerominia_ekdosis");
        if (dateEkdosis != null) apostoli.setImerominiaEkdosis(dateEkdosis.toLocalDate());

        Date dateAnaxorisis = rs.getDate("imerominia_anaxorisis");
        if (dateAnaxorisis != null) apostoli.setImerominiaAnaxorisis(dateAnaxorisis.toLocalDate());

        apostoli.setAntikatavoli(rs.getBigDecimal("antikatavoli"));
        apostoli.setParaliptis(rs.getString("paraliptis"));
        apostoli.setXora(rs.getString("xora"));
        apostoli.setPoli(rs.getString("poli"));
        apostoli.setTkParalipti(rs.getString("tk_paralipti"));
        apostoli.setDiefthinsi(rs.getString("diefthinsi"));
        apostoli.setTilefonoStathero(rs.getString("tilefono_stathero"));
        apostoli.setTilefonoKinito(rs.getString("tilefono_kinito"));
        apostoli.setIstoriko(rs.getString("istoriko"));
        apostoli.setStatusApostolis(rs.getString("status_apostolis"));
        apostoli.setStatusDetails(rs.getString("status_details"));
        apostoli.setStatusMypms(rs.getString("status_mypms"));
        apostoli.setSxolia(rs.getString("sxolia"));
        apostoli.setDeliveryFlag(rs.getInt("delivery_flag"));
        apostoli.setReturnedFlag(rs.getInt("returned_flag"));
        apostoli.setNonDeliveryReasonCode(rs.getString("non_delivery_reason_code"));
        apostoli.setShipmentStatus((Integer) rs.getObject("shipment_status"));
        apostoli.setDeliveryInfo(rs.getString("delivery_info"));
        apostoli.setStatusLocked(rs.getBoolean("status_locked"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) apostoli.setCreatedAt(createdAt.toLocalDateTime());

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) apostoli.setUpdatedAt(updatedAt.toLocalDateTime());

        return apostoli;
    }
}
