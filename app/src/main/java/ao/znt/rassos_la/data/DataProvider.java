package ao.znt.rassos_la.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ao.znt.rassos_la.R;
import ao.znt.rassos_la.models.Comerciante;

public final class DataProvider {

    private DataProvider() {
    }

    private static final Random RANDOM = new Random();

    public static List<Comerciante> getRandomData(int size) {
        List<Comerciante> coms = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            coms.add(createRandomComerciante());
        }
        return coms;
    }
    public static int[] getRandomImagens(int size) {
        int[] imgs = new int[size];
        for (int i = 0; i < size; i++) {
            imgs[i] = getRandomImagen();
        }
        return imgs;
    }

    public static Comerciante createRandomComerciante() {
        return new Comerciante(getRandomImagens(4), getRandomName()+" "+getRandomSurname(),getRandomServico(),getRandomEndereco(),(RANDOM.nextInt(10) + 5),(RANDOM.nextInt(10) + 5));
    }

    private static int getRandomImagen() {
        return imgs[RANDOM.nextInt(imgs.length)];
    }
    private static String getRandomName() { return names[RANDOM.nextInt(names.length)]; }
    private static String getRandomSurname() {
        return surnames[RANDOM.nextInt(surnames.length)];
    }
    private static String getRandomServico() {
        return servicos[RANDOM.nextInt(servicos.length)];
    }
    private static String getRandomEndereco() {
        return endereco[RANDOM.nextInt(endereco.length)];
    }


    private static  final int[] imgs =
    {
        R.drawable.banner_intro,
        R.drawable.login_banner,
            R.drawable.background_login,
            R.drawable.background_signup
    };
    private static final String[] endereco = new String[]{
            "Cazenga, Luanda",
            "Manhanga",
            "Viana",
            "Viana, Fofoca",
            "Viana Zango 3",
            "Viana Zango 4",
            "Viana Zango 1",
            "Viana Zango 5000",
            "Viana 11",
            "Kilamba",
            "KK",
            "Casenga Grafanil Bar",
            "Hingombota",
            "Sequele",
            "Calawenda, Cazenga",
            "Mabor",
            "30 praça nova",
            "Luanda sul",
            "Na minha casa",
            "Kalemba 2",
            "Kalemba 1",
            "Golfe 1",
            "Golfe 2",
            "Golfe quintalão do petro",
            "Aza branca",
            "Zamba 2",
            "Torres Dipanda",
            "Tamala",
            "Jennifer",
            "Elisha",
            "Brock",
            "Tyrone",
            "Aeoroporto",
            "Gameck Padaria",
            "Caçequel do buraco",
            "Cacuaco, Paraiso",
            "Cacuaco",
            "1* de Maio",
            "Retunda do Camama",
            "Viana, Ponte amarela",
            "Viana, Moagem",
            "Viana, Estalagem",
            "Viana, Gamenck",
            "Viana, Comarca",
            "Viana, Combal",
            "Terra Nova",
            "Munlevo de Sima",
            "Munlevo de Baixo",
            "Ria Seco"
    };
    private static final String[] servicos = new String[]{
            "Programador Mobile",
            "Programador front-end",
            "Programador back-end",
            "Designe digital",
            "Designe Web",
            "Modelador",
            "Cabelereiro",
            "Mecánico",
            "Raboteiro",
            "Pambaleiro",
            "Pedreiro",
            "Canalizador",
            "Dj",
            "Estucador",
            "Informático",
            "Comerciante-Cantina",
            "Farmácia esperança 1",
            "Offcina Amador prática",
            "Bancada da Madrinha",
            "Trabalhos de escola feito",
            "Studio Colora",
            "Ciber do Borja",
            "Ciber café",
            "Pambaleiro de livros",
            "Livraria digital (PDF)",
            "Testes da UAN em PDF",
            "PDF na Hora",
            "Motoquero Fantasma",
            "Cantina do Cené",
            "Centro Médica Medica",
            "Bar Cervejaria",
            "Pipocaria áPipocar",
            "Geladaria GelaMúcua",
            "Paderia Bom Sabor",
            "Rassos de Pen-Drive",
            "Compramos Bateria Estragada",
            "Arranjamos Máquina Estragada",
            "Carpinteiro",
            "Advogado",
            "Serrelheiro",
            "Professor ao domicílio"
    };

    private static final String[] names = new String[]{
            "Nickie",
            "Renita",
            "Celesta",
            "Kyra",
            "Debra",
            "Celsa",
            "Nan",
            "Tawanna",
            "Brittney",
            "Lizzette",
            "Myrtle",
            "Booker",
            "Pok",
            "Willodean",
            "Mardell",
            "Todd",
            "Lore",
            "Tandra",
            "Isidro",
            "Deandre",
            "Burton",
            "Cheryl",
            "Chong",
            "Willetta",
            "Karissa",
            "Dominque",
            "Zandra",
            "Tamala",
            "Jennifer",
            "Elisha",
            "Renate",
            "Shauna",
            "Latrisha",
            "Alayna",
            "Ana",
            "Ayana",
            "Arica",
            "Hal",
            "Marcelino",
            "Madeleine",
            "Hilton",
            "Erich",
            "Georgetta",
            "Maryrose",
            "Angelena",
            "Liana",
            "Santos",
            "Phil",
            "Annette",
            "Halina",
            "Elsy",
            "Brock",
            "Tyrone",
            "Myles",
            "Lang",
            "Micah",
            "Lee",
            "Lorraine",
            "Talia",
            "Eulalia",
            "Lavera",
            "Arlen",
            "Lena",
            "Julissa",
            "Mackenzie",
            "Lucy",
            "Juliette",
            "Tarra",
            "Clemente",
            "Ileana"
    };

    private static final String[] surnames = new String[]{
            "Slaymaker",
            "Heyer",
            "Oyer",
            "Fellman",
            "Moodie",
            "Shoaf",
            "Kurland",
            "Pollman",
            "Sheridan",
            "Whiting",
            "Walson",
            "Utt",
            "Roser",
            "Schoenberg",
            "Motsinger",
            "Corley",
            "Addis",
            "Ivers",
            "Llanos",
            "Braddy",
            "Clute",
            "Heroux",
            "Ezzell",
            "Pellett",
            "Shanks",
            "Manno",
            "Boehmer",
            "Dinapoli",
            "Fannin",
            "Phair",
            "Sampsel",
            "Sorg",
            "Canter",
            "Rutter",
            "Byler",
            "Hansen",
            "Mcgilvray",
            "Schley",
            "Cardinale",
            "Kennard",
            "Palmer",
            "Simoneaux",
            "Blomberg",
            "Simental",
            "Lazaro",
            "Carte",
            "Barrow",
            "Greenburg",
            "Maginnis",
            "Blume",
            "Wrench",
            "Coachman",
            "Arzate",
            "Soderquist",
            "Uhl",
            "Leggett",
            "Square",
            "Earnest",
            "Mctaggart",
            "Marrone",
            "Lanser",
            "Ricklefs",
            "Lukasiewicz",
            "Ines",
            "Holdren",
            "Brissette",
            "Dundas",
            "Ostrom",
            "Blake",
            "Roberds"
    };
}

