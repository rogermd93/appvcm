package com.example.appvcm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tournament.db";
    public static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_CAMPEON = "campeon";
    public static final String TABLE_CONFIGURACION = "configuracion";
    public static final String TABLE_INFORMACION_GRUPO = "informacionGrupo";
    public static final String TABLE_INSCRIPCION = "inscripcion";
    public static final String TABLE_JUGADOR = "jugador";
    public static final String TABLE_PARTIDOS = "partidos";
    public static final String TABLE_GRUPO = "grupo";
    public static final String TABLE_TORNEO = "torneo";
    public static final String TABLE_TIPO_CALENDARIO = "tipoCalendario";
    public static final String TABLE_TIPO_TORNEO = "tipoTorneo";

    // Create table statements
    private static final String CREATE_TABLE_CAMPEON = "CREATE TABLE IF NOT EXISTS " + TABLE_CAMPEON + " (" +
            "idTorneo INTEGER NOT NULL, " +
            "nombreJugador TEXT NOT NULL, " +
            "PRIMARY KEY(idTorneo, nombreJugador), " +
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(nombreJugador) REFERENCES " + TABLE_JUGADOR + "(apodo) ON UPDATE CASCADE ON DELETE CASCADE);";

    private static final String CREATE_TABLE_CONFIGURACION = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIGURACION + " (" +
            "idTorneo INTEGER, " +
            "ptVictoria INTEGER, " +
            "ptEmpate INTEGER, " +
            "ptDerrota INTEGER, " +
            "numTv INTEGER NOT NULL DEFAULT 1, " +
            "golExterior INTEGER, " +
            "diferenciaParticular INTEGER, " +
            "idTipoTorneo INTEGER NOT NULL, " +
            "idTipoCalendario INTEGER NOT NULL, " +
            "idaVueltaGrupo INTEGER, " +
            "idaVueltaEliminacion INTEGER, " +
            "idaVueltaFinal INTEGER, " +
            "numClasifGrupo INTEGER DEFAULT 0, " +
            "pequeñaFinal INTEGER DEFAULT 0, " +
            "PRIMARY KEY(idTorneo), " +
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(idTipoTorneo) REFERENCES " + TABLE_TIPO_TORNEO + "(idTipoTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(idTipoCalendario) REFERENCES " + TABLE_TIPO_CALENDARIO + "(idTipoCalendario) ON UPDATE CASCADE ON DELETE CASCADE);";

    private static final String CREATE_TABLE_INFORMACION_GRUPO = "CREATE TABLE IF NOT EXISTS " + TABLE_INFORMACION_GRUPO + " (" +
            "idTorneo INTEGER NOT NULL, " +
            "idGrupo INTEGER NOT NULL, " +
            "nombreGrupo TEXT, " +
            "numClasifFasesFinales INTEGER DEFAULT 2, " +
            "numClasifPerdedores INTEGER DEFAULT 0, " +
            "PRIMARY KEY(idTorneo, idGrupo), " +
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE);";

    private static final String CREATE_TABLE_INSCRIPCION = "CREATE TABLE IF NOT EXISTS " + TABLE_INSCRIPCION + " (" +
            "nombreJugador TEXT NOT NULL, " +
            "idTorneo INTEGER NOT NULL, " +
            "PRIMARY KEY(nombreJugador, idTorneo), " +
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(nombreJugador) REFERENCES " + TABLE_JUGADOR + "(apodo) ON UPDATE CASCADE ON DELETE CASCADE);";

    private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE IF NOT EXISTS " + TABLE_JUGADOR + " (" +
            "apodo TEXT NOT NULL, " +
            "correo TEXT DEFAULT NULL, " +
            "foto TEXT DEFAULT NULL, " +
            "nota INTEGER NOT NULL DEFAULT 50, " +
            "PRIMARY KEY(apodo));";

    private static final String CREATE_TABLE_PARTIDOS = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTIDOS + " (" +
            "idPartido INTEGER NOT NULL, " +
            "idTorneo INTEGER NOT NULL, " +
            "idGrupo INTEGER, " +
            "fecha TEXT, " +
            "numRonda INTEGER, " +
            "idJugador1 TEXT, " +
            "idJugador2 TEXT, " +
            "puntajeJ1 INTEGER, " +
            "puntajeJ2 INTEGER, " +
            "nombreGanador TEXT, " +
            "ingresado INTEGER NOT NULL DEFAULT 0, " +
            "numTv INTEGER NOT NULL DEFAULT 1, " +
            "ganadorPartido1 INTEGER, " +
            "ganadorPartido2 INTEGER, " +
            "numPartido INTEGER, " +
            "penalesJ1 INTEGER, " +
            "penalesJ2 INTEGER, " +
            "nota TEXT, " +
            "perdedorPartido1 INTEGER, " +
            "perdedorPartido2 INTEGER, " +
            "PRIMARY KEY(idPartido, idTorneo), " +
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(idJugador1) REFERENCES " + TABLE_JUGADOR + "(apodo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(idJugador2) REFERENCES " + TABLE_JUGADOR + "(apodo) ON UPDATE CASCADE ON DELETE CASCADE);";

    private static final String CREATE_TABLE_GRUPO = "CREATE TABLE IF NOT EXISTS " + TABLE_GRUPO + " (" +
            "idGrupo INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Autoincremental y clave primaria
            "idTorneo INTEGER NOT NULL, " +
            "nombreJugador TEXT NOT NULL, " +
            "numPuntos INTEGER DEFAULT 0, " +
            "victoria INTEGER DEFAULT 0, " +
            "empate INTEGER DEFAULT 0, " +
            "derrota INTEGER DEFAULT 0, " +
            "golesFavor INTEGER DEFAULT 0, " +
            "golesContra INTEGER DEFAULT 0, " +
            "diferencia INTEGER DEFAULT 0, " +
            "numPartidosJugados INTEGER DEFAULT 0, " +
            "posicion INTEGER DEFAULT 1, " +
            "UNIQUE(idTorneo, nombreJugador), " +  // Restringir la combinación a ser única
            "FOREIGN KEY(idTorneo) REFERENCES " + TABLE_TORNEO + "(idTorneo) ON UPDATE CASCADE ON DELETE CASCADE, " +
            "FOREIGN KEY(nombreJugador) REFERENCES " + TABLE_JUGADOR + "(apodo) ON UPDATE CASCADE ON DELETE CASCADE);";


    private static final String CREATE_TABLE_TORNEO = "CREATE TABLE IF NOT EXISTS " + TABLE_TORNEO + " (" +
            "idTorneo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombreTorneo TEXT NOT NULL, " +
            "numJugadores INTEGER DEFAULT 0, " +
            "fechaInicio TEXT DEFAULT NULL, " +
            "fechaFin TEXT DEFAULT NULL, " +
            "terminado INTEGER DEFAULT 0, " +
            "gruposTerminados INTEGER DEFAULT 0);";

    private static final String CREATE_TABLE_TIPO_CALENDARIO = "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_CALENDARIO + " (" +
            "idTipoCalendario INTEGER NOT NULL, " +
            "descripcion TEXT DEFAULT NULL, " +
            "PRIMARY KEY(idTipoCalendario));";

    private static final String CREATE_TABLE_TIPO_TORNEO = "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO_TORNEO + " (" +
            "idTipoTorneo INTEGER NOT NULL, " +
            "descripcion TEXT DEFAULT NULL, " +
            "PRIMARY KEY(idTipoTorneo));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CAMPEON);
        db.execSQL(CREATE_TABLE_CONFIGURACION);
        db.execSQL(CREATE_TABLE_INFORMACION_GRUPO);
        db.execSQL(CREATE_TABLE_INSCRIPCION);
        db.execSQL(CREATE_TABLE_JUGADOR);
        db.execSQL(CREATE_TABLE_PARTIDOS);
        db.execSQL(CREATE_TABLE_GRUPO);
        db.execSQL(CREATE_TABLE_TORNEO);
        db.execSQL(CREATE_TABLE_TIPO_CALENDARIO);
        db.execSQL(CREATE_TABLE_TIPO_TORNEO);
        db.beginTransaction();
        try {
            insertDefaultTorneos(db);
            insertDefaultCalendario(db);
            db.setTransactionSuccessful(); // Marks the transaction as successful
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception and log error
        } finally {
            db.endTransaction(); // End the transaction, commits if successful
        }
    }
    private void insertDefaultTorneos(SQLiteDatabase db) {
        try {
            db.execSQL("INSERT INTO tipoTorneo (idTipoTorneo, descripcion) VALUES (1, 'Campeonato');");
            db.execSQL("INSERT INTO tipoTorneo (idTipoTorneo, descripcion) VALUES (2, 'Eliminación Directa');");
            db.execSQL("INSERT INTO tipoTorneo (idTipoTorneo, descripcion) VALUES (3, 'Champions League');");
            db.execSQL("INSERT INTO tipoTorneo (idTipoTorneo, descripcion) VALUES (4, 'Copa del Mundo');");
            Log.d("DatabaseHelper", "Default Torneos inserted successfully");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting default torneos: " + e.getMessage());
        }
    }

    private void insertDefaultCalendario(SQLiteDatabase db) {
        try {
            db.execSQL("INSERT INTO tipoCalendario (idTipoCalendario, descripcion) VALUES (1, 'Calendario Regular');");
            Log.d("DatabaseHelper", "Default Calendario inserted successfully");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting default calendario: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPEON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIGURACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFORMACION_GRUPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSCRIPCION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRUPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TORNEO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_CALENDARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_TORNEO);
        onCreate(db);
    }
}