package univsys.asistenciadocente.seeders;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import univsys.asistenciadocente.models.*;
import univsys.asistenciadocente.repositories.*;

import java.time.*;
import java.util.*;

@Component
public class DatabaseSeeder {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ModuloRepository moduloRepository;
    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private CarreraMateriaRepository carreraMateriaRepository;
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @EventListener
    @Transactional
    public void seed(ContextRefreshedEvent event) {
        seedFacultades();
        seedRoleTable();
        seedUsersTable();
        seedCarreras();
        SeedMaterias();
        seedModulos();
        seedAulas();
        CarreraMateriaSeeder();
        semana();
        GrupoSeeder();
        asistenciaSeed();
        horariogrupos();


    }

    public void asistenciaSeed() {
        seedAsist("Presente", 1L, 3);
        seedAsist("Presente", 1L, 2);
        seedAsist("Presente", 1L, 1);
    }

    public void seedAsist(String estado, Long horarioId, int dias) {
        AsistenciaEntity asistencia = new AsistenciaEntity();
        asistencia.setEstado(estado);
        HorarioEntity horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario not found"));
        asistencia.setHorario(horario);
        LocalDate localDate = LocalDate.now().minusDays(dias);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, horario.getInicio());
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        asistencia.setFecha(date);
        asistenciaRepository.save(asistencia);
    }
    public void horariogrupos (){
        asignargrupo(1L,1L);
        asignargrupo(1L,2L);
        asignargrupo(1L,5281L);
        asignargrupo(1L,5282L);
        asignargrupo(1L,10561L);
        asignargrupo(1L,10562L);
        asignargrupo(2L,3L);
        asignargrupo(2L,4L);
        asignargrupo(2L,5283L);
        asignargrupo(2L,5284L);
        asignargrupo(2L,10563L);
        asignargrupo(2L,10564L);
        asignargrupo(3L,5L);
        asignargrupo(3L,6L);
        asignargrupo(3L,5285L);
        asignargrupo(3L,5286L);
        asignargrupo(3L,10565L);
        asignargrupo(3L,10566L);

    }
@Transactional
    public void asignargrupo(Long idgrupo, Long idhorario) {
        HorarioEntity horario = horarioRepository.findById(idhorario).orElseThrow(() -> new RuntimeException("Horario not found"));
        GrupoEntity grupo = grupoRepository.findById(idgrupo).orElseThrow(() -> new RuntimeException("Grupo not found"));
        horario.setGrupo(grupo);
        horarioRepository.save(horario);
    }

    public void GrupoSeeder() {
        seedGrupo("NW", "1-2024", 1L, 4L);
        seedGrupo("SC", "1-2024", 1L, 4L);
        seedGrupo("SA", "1-2024", 1L, 5L);
        seedGrupo("SB", "1-2024", 1L, 5L);
        seedGrupo("NW", "1-2024", 2L, 6L);
        seedGrupo("SC", "1-2024", 2L, 6L);
        seedGrupo("SA", "1-2024", 2L, 7L);
        seedGrupo("SB", "1-2024", 2L, 7L);
        seedGrupo("NW", "2-2023", 1L, 4L);
        seedGrupo("SC", "2-2023", 1L, 4L);
        seedGrupo("SA", "2-2023", 1L, 5L);
        seedGrupo("SB", "2-2023", 1L, 5L);
        seedGrupo("NW", "2-2023", 2L, 6L);
        seedGrupo("SC", "2-2023", 2L, 6L);
        seedGrupo("SA", "2-2023", 2L, 7L);
        seedGrupo("SB", "3-2023", 2L, 7L);

    }

    public void seedGrupo(String nombre, String periodo, Long materiaId, Long userId) {
        GrupoEntity grup = new GrupoEntity();
        grup.setNombre(nombre);
        grup.setPeriodo(periodo);
        grup.setMateria(materiaRepository.findById(materiaId).get());
        grup.setUser(userRepository.findById(userId).get());
        grupoRepository.save(grup);
    }
    public void semana () {
        seedHorarios("Lunes");
        seedHorarios("Martes");
        seedHorarios("Miercoles");
        seedHorarios("Jueves");
        seedHorarios("Viernes");
        seedHorarios("Sabado");
    }

    public void seedHorarios(String dia) {
        Iterable<AulaEntity> aulas = aulaRepository.findAll();
        for (AulaEntity aula : aulas) {
            Long aulaId = aula.getId();
            horarioSeed(aulaId, "07:00", "07:45",dia);
            horarioSeed(aulaId, "07:45", "08:30",dia);
            horarioSeed(aulaId, "08:30", "09:15",dia);
            horarioSeed(aulaId, "09:15", "10:00",dia);
            horarioSeed(aulaId, "10:00", "10:45",dia);
            horarioSeed(aulaId, "10:45", "11:30",dia);
            horarioSeed(aulaId, "11:30", "12:15",dia);
            horarioSeed(aulaId, "12:15", "13:00",dia);
            horarioSeed(aulaId, "13:45", "14:30",dia);
            horarioSeed(aulaId, "14:30", "15:15",dia);
            horarioSeed(aulaId, "15:15", "16:00",dia);
        }
    }

    public void horarioSeed(Long aulaID, String Sinicio, String Sfin,String dia) {
        Optional<AulaEntity> aula = aulaRepository.findById(aulaID);
        if (aula.isPresent()) {
            LocalTime fin = LocalTime.parse(Sfin);
            LocalTime inicio = LocalTime.parse(Sinicio);
            HorarioEntity horario = new HorarioEntity();
            horario.setAula(aula.get());
            horario.setInicio(inicio);
            horario.setFin(fin);
            horario.setDia(dia);
            horarioRepository.save(horario);
        }
    }

    public void seedAulas() {
        for (Long moduloId = 1L; moduloId <= 10L; moduloId++) {
            for (int aulaNumero = 1; aulaNumero <= 24; aulaNumero++) {
                aulaSeed(aulaNumero, moduloId);
            }
        }
    }

    private void aulaSeed(int numero, Long idModulo) {
        Optional<ModuloEntity> modulo = moduloRepository.findById(idModulo);
        if (modulo.isPresent()) {
            AulaEntity aula = new AulaEntity();
            aula.setNumero(numero);
            aula.setModulo(modulo.get());
            aula.setCapacidad(40);
            aulaRepository.save(aula);
        }
    }

    public void seedModulos() {
        moduloSeed(220);
        moduloSeed(221);
        moduloSeed(222);
        moduloSeed(232);
        moduloSeed(223);
        moduloSeed(224);
        moduloSeed(225);
        moduloSeed(226);
        moduloSeed(236);
        moduloSeed(237);
        moduloSeed(666);
    }

    public void CarreraMateriaSeeder() {
        for (int i = 1; i < 17; i++) {
            Long materia = (long) i;
            carreraMateriaSeed(1 + i / 5, 1L, materia);
            carreraMateriaSeed(1 + i / 5, 2L, materia);
            carreraMateriaSeed(1 + i / 5, 3L, materia);
        }
        for (int i = 1; i < 11; i++) {
            Long materia = (long) i + 16;
            carreraMateriaSeed(1 + i / 5, 4L, materia);
            carreraMateriaSeed(1 + i / 5, 5L, materia);
            carreraMateriaSeed(1 + i / 5, 6L, materia);
        }
    }

    private void carreraMateriaSeed(int nivel, Long carreraId, Long materiaId) {
        CarreraMateriaEntity carreraMateria = new CarreraMateriaEntity();
        // Obtener las entidades de carrera y materia por sus IDs (usando tus repositorios)
        try {
            Optional<CarreraEntity> carrera = carreraRepository.findById(carreraId);
            Optional<MateriaEntity> materia = materiaRepository.findById(materiaId);
            carreraMateria.setCarrera(carrera.get());
            carreraMateria.setMateria(materia.get());
            carreraMateria.setNivel(nivel);
            carreraMateriaRepository.save(carreraMateria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void moduloSeed(int num) {
        ModuloEntity mod = new ModuloEntity();
        mod.setNumero(num);
        moduloRepository.save(mod);
    }

    public void SeedMaterias() {
        //Sistemas
        materiaseed("Calculo I", "MAT-101");
        materiaseed("Calculo II", "MAT-102");
        materiaseed("Ecuaciones Dif.", "MAT-207");
        materiaseed("Introduccion a la informatica", "INF-110");
        materiaseed("Fisica I", "FIS-100");
        materiaseed("Fisica II", "FIS-102");
        materiaseed("Fisica III", "FIS-300");
        materiaseed("Ingles I", "LIN-100");
        materiaseed("Ingles II", "LIN-101");
        materiaseed("Administracion", "ADM-100");
        materiaseed("Arq. de Computadoras", "INF-211");
        materiaseed("Programacion II", "INF-210");
        materiaseed("Programacion I", "INF-120");
        materiaseed("Programacion Ensamblador", "INF-333");
        materiaseed("Estructuras de Datos I", "INF-220");
        materiaseed("Estructuras de Datos II", "INF-310");
        //industrial
        materiaseed("Algebra I", "MAT-100");
        materiaseed("Dibujo Tecnico I", "MEC-101");
        materiaseed("Dibujo Tecnico II", "MEC-103");
        materiaseed("Quimica General", "QMC-100");
        materiaseed("Quimica Organica I", "QMC-200");
        materiaseed("Fisico-Quimica I", "QMC-206");
        materiaseed("Probabilidad y Estadistica", "IND-110");
        materiaseed("Informatica I", "INF-243");
        materiaseed("teoria de Circuitos I", "ELT-211");
        materiaseed("teoria de Circuitos II", "ELT-311");
    }

    private void materiaseed(String nombreMateria, String siglaMateria) {
        MateriaEntity materia = new MateriaEntity();
        materia.setName(nombreMateria);
        materia.setSigla(siglaMateria);
        materiaRepository.save(materia);
    }

    public void seedCarreras() {
        carreraseed("Sistemas", "INF-204", 5L);
        carreraseed("Redes", "INF-205", 5L);
        carreraseed("Informatica", "INF-206", 5L);
        carreraseed("Industrial", "IND-102", 2L);
        carreraseed("Alimentos", "ALM-200", 2L);
        carreraseed("Electronica", "ELE-201", 2L);
        carreraseed("Contaduria", "CON-300", 1L);
        carreraseed("Medicina", "MED-207", 3L);
        carreraseed("Enfermeria", "ENF-208", 3L);
        carreraseed("Psicologia", "PSY-209", 4L);
        carreraseed("Derecho", "DER-255", 6L);
    }

    private void carreraseed(String nombreCarrera, String siglaCarrera, Long idFacultad) {
        Optional<FacultadEntity> facultad = facultadRepository.findById(idFacultad);
        if (facultad.isPresent()) {
            CarreraEntity carre = new CarreraEntity();
            carre.setName(nombreCarrera);
            carre.setSigla(siglaCarrera);
            carre.setFacultad(facultad.get());
            carreraRepository.save(carre);
        }
    }

    public void seedFacultades() {
        List<String> nombresFacultades = new ArrayList<>();
        nombresFacultades.add("Contaduria");
        nombresFacultades.add("Ciencias Exactas");
        nombresFacultades.add("Medicina");
        nombresFacultades.add("Humanidades");
        nombresFacultades.add("Ciencias de la Computacion");
        nombresFacultades.add("Derecho");
        nombresFacultades.add("Veterinaria");
        for (String nombre : nombresFacultades) {
            FacultadEntity facultad = new FacultadEntity();
            facultad.setName(nombre);
            facultadRepository.save(facultad);
        }
    }

    private void seedRoleTable() {
        rolSeed("USER");
        rolSeed("ADMIN");
    }

    public void rolSeed(String nombre) {
        RoleEntity Rol = new RoleEntity();
        Rol.setName(nombre);
        roleRepository.save(Rol);
    }

    private void seedUsersTable() {
        userSeed("Administrador General", "admin", "0000", "ADMIN");
        userSeed("Victor Ciprian Ortuño", "Vicio", "0000", "ADMIN");
        userSeed("Mario Rios Contreras", "Mari", "0000", "ADMIN");
        userSeed("Alejandro Salazar", "docente", "0000", "USER");
        userSeed("Manuel Pedraza", "Mape", "0000", "USER");
        userSeed("Michael Suarez", "Michu", "0000", "USER");
        userSeed("Angela Carrasco", "Angy", "0000", "USER");
        userSeed("Anita Mendoza", "Amen", "0000", "USER");
        userSeed("Olga Botegga", "Obo", "0000", "USER");
    }

    private void userSeed(String name, String nick, String password, String rol) {
        if (userRepository.findByUsername(name).isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(nick + "@gmail.com");
            user.setRol(rol);
            user.setActive(true);
            userRepository.save(user);
        }
    }
}

