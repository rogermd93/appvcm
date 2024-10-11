package com.example.appvcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.appvcm.clases.Jugador;
import com.example.appvcm.clases.JugadorDetalle;
import com.example.appvcm.clases.JugadorGrupo;
import com.example.appvcm.clases.Partido;
import com.example.appvcm.clases.Tournament;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        insertDefaultDataIfNeeded();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Partido> obtenerPartidosDesdeBD(int idTorneo) {
        List<Partido> partidos = new ArrayList<>();

        // Definir columnas para extraer de la tabla grupo
        String[] columns = {
                "idTorneo", "idGrupo", "nombreJugador", "numPuntos", "victoria", "empate",
                "derrota", "golesFavor", "golesContra", "diferencia", "numPartidosJugados"
        };

        // Consultar todos los jugadores del torneo
        Cursor cursor = database.query("grupo", columns, "idTorneo = ?", new String[]{String.valueOf(idTorneo)}, null, null, null);

        if (cursor.moveToFirst()) {
            List<JugadorGrupo> jugadoresGrupo = new ArrayList<>();

            // Recorrer el cursor para generar los jugadores y almacenarlos
            do {
                int torneoId = cursor.getInt(cursor.getColumnIndexOrThrow("idTorneo"));
                int grupoId = cursor.getInt(cursor.getColumnIndexOrThrow("idGrupo"));
                String nombreJugador = cursor.getString(cursor.getColumnIndexOrThrow("nombreJugador"));
                int numPuntos = cursor.getInt(cursor.getColumnIndexOrThrow("numPuntos"));
                int victoria = cursor.getInt(cursor.getColumnIndexOrThrow("victoria"));
                int empate = cursor.getInt(cursor.getColumnIndexOrThrow("empate"));
                int derrota = cursor.getInt(cursor.getColumnIndexOrThrow("derrota"));
                int golesFavor = cursor.getInt(cursor.getColumnIndexOrThrow("golesFavor"));
                int golesContra = cursor.getInt(cursor.getColumnIndexOrThrow("golesContra"));
                int diferencia = cursor.getInt(cursor.getColumnIndexOrThrow("diferencia"));
                int numPartidosJugados = cursor.getInt(cursor.getColumnIndexOrThrow("numPartidosJugados"));

                // Crear objeto JugadorGrupo
                JugadorGrupo jugador = new JugadorGrupo(torneoId, grupoId, nombreJugador, numPuntos, victoria, empate,
                        derrota, golesFavor, golesContra, diferencia, numPartidosJugados);

                jugadoresGrupo.add(jugador);
            } while (cursor.moveToNext());

            // Generar partidos en función de los jugadores leídos
            for (int i = 0; i < jugadoresGrupo.size(); i++) {
                for (int j = i + 1; j < jugadoresGrupo.size(); j++) {
                    // Crear partidos con los goles almacenados
                    JugadorGrupo jugador1 = jugadoresGrupo.get(i);
                    JugadorGrupo jugador2 = jugadoresGrupo.get(j);

                    Partido partido = new Partido(jugador1, jugador2);
                    partido.setGolesJugador1(jugador1.getGolesFavor());
                    partido.setGolesJugador2(jugador2.getGolesFavor());

                    partidos.add(partido);
                }
            }
        }

        cursor.close();
        return partidos;
    }



    public JugadorGrupo obtenerJugadorGrupo(String nombreJugador, int idTorneo) {
        // Consulta para obtener el JugadorGrupo desde la base de datos
        String query = "SELECT * FROM grupo WHERE nombreJugador = ? AND idTorneo = ?";
        Cursor cursor = database.rawQuery(query, new String[]{nombreJugador, String.valueOf(idTorneo)});

        JugadorGrupo jugador = null;
        if (cursor.moveToFirst()) {
            // Crear el objeto JugadorGrupo con los datos obtenidos
            jugador = new JugadorGrupo(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTorneo")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idGrupo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombreJugador")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("numPuntos")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("victoria")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("empate")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("derrota")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("golesFavor")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("golesContra")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("diferencia")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("numPartidosJugados")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("posicion"))
            );
        }
        cursor.close();
        return jugador;
    }



    public List<JugadorGrupo> cargarJugadoresDesdeBD(int idTorneo) {
        List<JugadorGrupo> jugadoresGrupo = new ArrayList<>();

        // Definir columnas para extraer de la tabla grupo
        String[] columns = {
                "nombreJugador",  // Sin DISTINCT en el nombre de la columna
                "idTorneo",
                "idGrupo",
                "numPuntos",
                "victoria",
                "empate",
                "derrota",
                "golesFavor",
                "golesContra",
                "diferencia",
                "numPartidosJugados",
                "posicion"
        };

        // Definir la condición para seleccionar jugadores de un torneo específico
        String selection = "idTorneo = ?";
        String[] selectionArgs = {String.valueOf(idTorneo)};

        // Ejecutar la consulta
        Cursor cursor = database.query(true, "grupo", columns, selection, selectionArgs, "nombreJugador", null, null, null); // `true` y `GROUP BY nombreJugador` para evitar duplicados

        if (cursor.moveToFirst()) {
            do {
                // Obtenemos la información de cada jugador (sin duplicar)
                String nombreJugador = cursor.getString(cursor.getColumnIndexOrThrow("nombreJugador"));
                int torneoId = cursor.getInt(cursor.getColumnIndexOrThrow("idTorneo"));
                int idGrupo = cursor.getInt(cursor.getColumnIndexOrThrow("idGrupo"));
                int numPuntos = cursor.getInt(cursor.getColumnIndexOrThrow("numPuntos"));
                int victoria = cursor.getInt(cursor.getColumnIndexOrThrow("victoria"));
                int empate = cursor.getInt(cursor.getColumnIndexOrThrow("empate"));
                int derrota = cursor.getInt(cursor.getColumnIndexOrThrow("derrota"));
                int golesFavor = cursor.getInt(cursor.getColumnIndexOrThrow("golesFavor"));
                int golesContra = cursor.getInt(cursor.getColumnIndexOrThrow("golesContra"));
                int diferencia = cursor.getInt(cursor.getColumnIndexOrThrow("diferencia"));
                int numPartidosJugados = cursor.getInt(cursor.getColumnIndexOrThrow("numPartidosJugados"));
                int posicion = cursor.getInt(cursor.getColumnIndexOrThrow("posicion"));

                // Crear un objeto JugadorGrupo y añadirlo a la lista
                JugadorGrupo jugador = new JugadorGrupo(torneoId, idGrupo, nombreJugador, numPuntos, victoria, empate,
                        derrota, golesFavor, golesContra, diferencia, numPartidosJugados, posicion);
                jugadoresGrupo.add(jugador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jugadoresGrupo;
    }

    public Cursor getConfiguracionByTorneo(int idTorneo) {
        String query = "SELECT * FROM configuracion WHERE idTorneo = ?";
        return database.rawQuery(query, new String[]{String.valueOf(idTorneo)});
    }

    // Obtener el siguiente idGrupo disponible para un torneo específico
    public int getNextIdGrupo(int idTorneo) {
        String query = "SELECT MAX(idGrupo) FROM grupo WHERE idTorneo = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(idTorneo)});
        int nextIdGrupo = 1;  // Empezar en 1 si no hay grupos
        if (cursor.moveToFirst()) {
            int maxIdGrupo = cursor.getInt(0);  // Obtener el idGrupo máximo actual
            if (!cursor.isNull(0)) {
                nextIdGrupo = maxIdGrupo + 1;  // Sumar 1 al grupo más alto
            }
        }
        cursor.close();
        return nextIdGrupo;
    }
    public void updateTorneo(int idTorneo, String fechaFin, int terminado, int gruposTerminados) {
        ContentValues values = new ContentValues();
        values.put("fechaFin", fechaFin);
        values.put("terminado", terminado);
        values.put("gruposTerminados", gruposTerminados);

        // Update the TABLE_TORNEO
        database.update("torneo", values, "idTorneo = ?", new String[]{String.valueOf(idTorneo)});
    }
    public List<Tournament> getTournamentsByStatus(int status) {
        List<Tournament> tournaments = new ArrayList<>();

        // Ensure you are using the correct column names based on your database schema
        Cursor cursor = database.rawQuery("SELECT t.idTorneo, t.nombreTorneo, t.fechaInicio, tt.descripcion " +
                "FROM torneo t " +
                "JOIN configuracion c ON t.idTorneo = c.idTorneo " +
                "JOIN tipoTorneo tt ON c.idTipoTorneo = tt.idTipoTorneo " +
                "WHERE t.terminado = ?", new String[]{String.valueOf(status)});

        if (cursor.moveToFirst()) {
            do {
                Tournament tournament = new Tournament(
                        cursor.getString(cursor.getColumnIndexOrThrow("nombreTorneo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("fechaInicio")),
                        cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                        cursor.getString(cursor.getColumnIndexOrThrow("idTorneo"))
                );
                tournaments.add(tournament);
            } while (cursor.moveToNext());
        }

        cursor.close(); // Always close the cursor after use
        return tournaments;
    }



    public int updateGrupo(int idTorneo, int idGrupo, String nombreJugador, int numPuntos, int victoria, int empate,
                           int derrota, int golesFavor, int golesContra, int diferencia, int numPartidosJugados) {
        ContentValues values = new ContentValues();
        values.put("numPuntos", numPuntos);
        values.put("victoria", victoria);
        values.put("empate", empate);
        values.put("derrota", derrota);
        values.put("golesFavor", golesFavor);
        values.put("golesContra", golesContra);
        values.put("diferencia", diferencia);
        values.put("numPartidosJugados", numPartidosJugados);

        // Actualizar la tabla grupo
        return database.update("grupo", values,
                "idTorneo = ? AND idGrupo = ? AND nombreJugador = ?",
                new String[]{String.valueOf(idTorneo), String.valueOf(idGrupo), nombreJugador});
    }

    // Insert a player
    public long insertPlayer(String apodo, String correo, String foto, int nota) {
        try{
            ContentValues values = new ContentValues();
            values.put("apodo", apodo);
            values.put("correo", correo);
            values.put("foto", foto);
            values.put("nota", nota);
            return database.insert("jugador", null, values);
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return -1;
        }
    }

    // Insert a campeon
    public long insertCampeon(int idTorneo, String nombreJugador) {
        ContentValues values = new ContentValues();
        values.put("idTorneo", idTorneo);
        values.put("nombreJugador", nombreJugador);
        return database.insert("campeon", null, values);
    }

    // Insert a configuracion
    public long insertConfiguracion(int idTorneo, int ptVictoria, int ptEmpate, int ptDerrota, int numTv,
                                    int golExterior, int diferenciaParticular, int idTipoTorneo, int idTipoCalendario,
                                    int idaVueltaGrupo, int idaVueltaEliminacion, int idaVueltaFinal, int numClasifGrupo, int pequeñaFinal) {
        long result = -1;  // Inicializa el valor de retorno con -1, que indica un fallo
        ContentValues values = new ContentValues();
        values.put("idTorneo", idTorneo);
        values.put("ptVictoria", ptVictoria);
        values.put("ptEmpate", ptEmpate);
        values.put("ptDerrota", ptDerrota);
        values.put("numTv", numTv);
        values.put("golExterior", golExterior);
        values.put("diferenciaParticular", diferenciaParticular);
        values.put("idTipoTorneo", idTipoTorneo);
        values.put("idTipoCalendario", idTipoCalendario);
        values.put("idaVueltaGrupo", idaVueltaGrupo);
        values.put("idaVueltaEliminacion", idaVueltaEliminacion);
        values.put("idaVueltaFinal", idaVueltaFinal);
        values.put("numClasifGrupo", numClasifGrupo);
        values.put("pequeñaFinal", pequeñaFinal);

        // Maneja la inserción con un bloque try-catch
        try {
            result = database.insert("configuracion", null, values);
            if (result == -1) {
                Log.e("DatabaseError", "Error al insertar la configuración en la base de datos.");
            } else {
                Log.d("DatabaseSuccess", "Configuración insertada exitosamente con ID: " + result);
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Excepción al insertar la configuración: " + e.getMessage());
        }

        return result;
    }


    // Insert a informacionGrupo
    public long insertInformacionGrupo(int idTorneo, int idGrupo, String nombreGrupo, int numClasifFasesFinales, int numClasifPerdedores) {
        ContentValues values = new ContentValues();
        values.put("idTorneo", idTorneo);
        values.put("idGrupo", idGrupo);
        values.put("nombreGrupo", nombreGrupo);
        values.put("numClasifFasesFinales", numClasifFasesFinales);
        values.put("numClasifPerdedores", numClasifPerdedores);
        return database.insert("informacionGrupo", null, values);
    }

    // Insert a inscripcion
    public long insertInscripcion(String nombreJugador, int idTorneo) {
        ContentValues values = new ContentValues();
        values.put("nombreJugador", nombreJugador);
        values.put("idTorneo", idTorneo);
        return database.insert("inscripcion", null, values);
    }

    // Insert a partido
    public long insertPartido(int idPartido, int idTorneo, Integer idGrupo, String fecha, Integer numRonda,
                              String idJugador1, String idJugador2, Integer puntajeJ1, Integer puntajeJ2,
                              String nombreGanador, int ingresado, int numTv, Integer ganadorPartido1,
                              Integer ganadorPartido2, Integer numPartido, Integer penalesJ1,
                              Integer penalesJ2, String nota, Integer perdedorPartido1, Integer perdedorPartido2) {
        ContentValues values = new ContentValues();
        values.put("idPartido", idPartido);
        values.put("idTorneo", idTorneo);
        values.put("idGrupo", idGrupo);
        values.put("fecha", fecha);
        values.put("numRonda", numRonda);
        values.put("idJugador1", idJugador1);
        values.put("idJugador2", idJugador2);
        values.put("puntajeJ1", puntajeJ1);
        values.put("puntajeJ2", puntajeJ2);
        values.put("nombreGanador", nombreGanador);
        values.put("ingresado", ingresado);
        values.put("numTv", numTv);
        values.put("ganadorPartido1", ganadorPartido1);
        values.put("ganadorPartido2", ganadorPartido2);
        values.put("numPartido", numPartido);
        values.put("penalesJ1", penalesJ1);
        values.put("penalesJ2", penalesJ2);
        values.put("nota", nota);
        values.put("perdedorPartido1", perdedorPartido1);
        values.put("perdedorPartido2", perdedorPartido2);
        return database.insert("partidos", null, values);
    }

    // Insert a grupo
    public long insertGrupo(int idTorneo, int idGrupo, String nombreJugador, int numPuntos, int victoria, int empate,
                            int derrota, int golesFavor, int golesContra, int diferencia, int numPartidosJugados, int posicion) {
        ContentValues values = new ContentValues();
        values.put("idTorneo", idTorneo);
        values.put("nombreJugador", nombreJugador);
        values.put("numPuntos", numPuntos);
        values.put("victoria", victoria);
        values.put("empate", empate);
        values.put("derrota", derrota);
        values.put("golesFavor", golesFavor);
        values.put("golesContra", golesContra);
        values.put("diferencia", diferencia);
        values.put("numPartidosJugados", numPartidosJugados);
        values.put("posicion", posicion);

        // Insertar el registro en la tabla
        try {
            long rowId = database.insert("grupo", null, values);
            if (rowId == -1) {
                Log.e("InsertGrupo", "Error en la inserción del grupo.");
            }
        } catch (Exception e) {
            Log.e("InsertGrupo", "Excepción durante la inserción: " + e.getMessage());
        }

        // Consultar el idGrupo para devolverlo
        Cursor cursor = database.query("grupo", new String[]{"idGrupo"},
                "idTorneo = ? AND nombreJugador = ?",
                new String[]{String.valueOf(idTorneo), nombreJugador},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int insertedIdGrupo = cursor.getInt(cursor.getColumnIndexOrThrow("idGrupo"));
            cursor.close();
            return insertedIdGrupo;
        } else {
            // Manejar caso donde no se pudo encontrar el grupo recién insertado
            return -1;
        }
    }


    // Insert a torneo
    public long insertTorneo(String nombreTorneo, int numJugadores, String fechaInicio, String fechaFin, int terminado, int gruposTerminados) {
        ContentValues values = new ContentValues();
        values.put("nombreTorneo", nombreTorneo);
        values.put("numJugadores", numJugadores);
        values.put("fechaInicio", fechaInicio);
        values.put("fechaFin", fechaFin);
        values.put("terminado", terminado);
        values.put("gruposTerminados", gruposTerminados);
        return database.insert("torneo", null, values);
    }

    // Insert a tipoCalendario
    public long insertTipoCalendario(int idTipoCalendario, String descripcion) {
        ContentValues values = new ContentValues();
        values.put("idTipoCalendario", idTipoCalendario);
        values.put("descripcion", descripcion);
        return database.insert("tipoCalendario", null, values);
    }

    // Insert a tipoTorneo
    public long insertTipoTorneo(int idTipoTorneo, String descripcion) {
        ContentValues values = new ContentValues();
        values.put("idTipoTorneo", idTipoTorneo);
        values.put("descripcion", descripcion);
        return database.insert("tipoTorneo", null, values);
    }

    // Get all players
    public List<Jugador> getAllPlayers() {
        List<Jugador> players = new ArrayList<>();
        Cursor cursor = database.query("jugador", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int apodoIndex = cursor.getColumnIndexOrThrow("apodo");
            int correoIndex = cursor.getColumnIndexOrThrow("correo");
            int fotoIndex = cursor.getColumnIndexOrThrow("foto");
            int notaIndex = cursor.getColumnIndexOrThrow("nota");
            do {
                String apodo = cursor.getString(apodoIndex);
                String correo = cursor.getString(correoIndex);
                String foto = cursor.getString(fotoIndex);
                int nota = cursor.getInt(notaIndex);
                players.add(new Jugador(apodo, correo, foto, nota));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return players;
    }
    public List<JugadorDetalle> getAllPlayerDetails() {
        List<JugadorDetalle> playerDetails = new ArrayList<>();

        // Consulta para obtener los detalles de cada jugador en la tabla grupo
        String query = "SELECT " +
                "    jugador.apodo, " +
                "    jugador.correo, " +
                "    jugador.foto, " +
                "    jugador.nota, " +
                "    COALESCE(SUM(grupo.victoria), 0) AS partidosGanados, " +
                "    COALESCE(SUM(grupo.empate), 0) AS partidosEmpatados, " +
                "    COALESCE(SUM(grupo.derrota), 0) AS partidosPerdidos, " +
                "    COALESCE(SUM(grupo.golesFavor), 0) AS golesFavor, " +
                "    COALESCE(SUM(grupo.golesContra), 0) AS golesContra, " +
                "    COUNT(grupo.idGrupo) AS partidosJugados, " +
                "    COALESCE((SELECT COUNT(*) FROM torneo WHERE torneo.idTorneo = grupo.idTorneo), 0) AS campeonatos " +
                "FROM " +
                "    jugador " +
                "LEFT JOIN " +
                "    grupo ON grupo.nombreJugador = jugador.apodo " +
                "GROUP BY " +
                "    jugador.apodo, jugador.correo, jugador.foto, jugador.nota;";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int apodoIndex = cursor.getColumnIndexOrThrow("apodo");
            int correoIndex = cursor.getColumnIndexOrThrow("correo");
            int fotoIndex = cursor.getColumnIndexOrThrow("foto");
            int notaIndex = cursor.getColumnIndexOrThrow("nota");
            int partidosGanadosIndex = cursor.getColumnIndexOrThrow("partidosGanados");
            int partidosEmpatadosIndex = cursor.getColumnIndexOrThrow("partidosEmpatados");
            int partidosPerdidosIndex = cursor.getColumnIndexOrThrow("partidosPerdidos");
            int golesFavorIndex = cursor.getColumnIndexOrThrow("golesFavor");
            int golesContraIndex = cursor.getColumnIndexOrThrow("golesContra");
            int partidosJugadosIndex = cursor.getColumnIndexOrThrow("partidosJugados");
            int campeonatosIndex = cursor.getColumnIndexOrThrow("campeonatos");

            do {
                String apodo = cursor.getString(apodoIndex);
                String correo = cursor.getString(correoIndex);
                String foto = cursor.getString(fotoIndex);
                int nota = cursor.getInt(notaIndex);
                int partidosGanados = cursor.getInt(partidosGanadosIndex);
                int partidosEmpatados = cursor.getInt(partidosEmpatadosIndex);
                int partidosPerdidos = cursor.getInt(partidosPerdidosIndex);
                int golesFavor = cursor.getInt(golesFavorIndex);
                int golesContra = cursor.getInt(golesContraIndex);
                int partidosJugados = cursor.getInt(partidosJugadosIndex);
                int campeonatos = cursor.getInt(campeonatosIndex);

                // Aquí puedes ajustar los valores de eliminatorias, championsLeague, etc.
                int eliminatorias = 0;  // Asigna el valor real si lo tienes disponible
                int championsLeague = 0;
                int copasMundo = 0;

                // Crear instancia de JugadorDetalle
                JugadorDetalle jugadorDetalle = new JugadorDetalle(
                        apodo, correo, foto, nota,
                        campeonatos, eliminatorias, championsLeague, copasMundo,
                        partidosGanados, partidosEmpatados, partidosPerdidos,
                        golesFavor, golesContra, partidosJugados
                );

                playerDetails.add(jugadorDetalle);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return playerDetails;
    }

    // Get all torneos
    public List<String> getAllTorneos() {
        List<String> torneos = new ArrayList<>();
        Cursor cursor = database.query("torneo", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nombreTorneoIndex = cursor.getColumnIndexOrThrow("nombreTorneo");
            do {
                String nombreTorneo = cursor.getString(nombreTorneoIndex);
                torneos.add(nombreTorneo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return torneos;
    }

    // Get all partidos
    public List<String> getAllPartidos() {
        List<String> partidos = new ArrayList<>();
        Cursor cursor = database.query("partidos", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int fechaIndex = cursor.getColumnIndexOrThrow("fecha");
            do {
                String fecha = cursor.getString(fechaIndex);
                partidos.add(fecha);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return partidos;
    }
    private void insertDefaultDataIfNeeded() {
        // Check if tipoTorneo has data
        if (!isTablePopulated("tipoTorneo")) {
            insertDefaultTorneos();
        }

        // Check if tipoCalendario has data
        if (!isTablePopulated("tipoCalendario")) {
            insertDefaultCalendario();
        }
    }

    // Check if a table has data
    private boolean isTablePopulated(String tableName) {
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        boolean populated = false;
        if (cursor.moveToFirst()) {
            populated = cursor.getInt(0) > 0; // If count > 0, the table is populated
        }
        cursor.close();
        return populated;
    }

    // Insert default tipoTorneo data
    private void insertDefaultTorneos() {
        ContentValues values = new ContentValues();

        values.put("idTipoTorneo", 1);
        values.put("descripcion", "Campeonato");
        database.insert("tipoTorneo", null, values);

        values.put("idTipoTorneo", 2);
        values.put("descripcion", "Eliminación Directa");
        database.insert("tipoTorneo", null, values);

        values.put("idTipoTorneo", 3);
        values.put("descripcion", "Champions League");
        database.insert("tipoTorneo", null, values);

        values.put("idTipoTorneo", 4);
        values.put("descripcion", "Copa del Mundo");
        database.insert("tipoTorneo", null, values);
    }

    // Insert default tipoCalendario data
    private void insertDefaultCalendario() {
        ContentValues values = new ContentValues();

        values.put("idTipoCalendario", 1);
        values.put("descripcion", "Calendario Regular");
        database.insert("tipoCalendario", null, values);
    }
}