package tugas;

import java.util.*;

public class RuteMakanan {

    static class TempatMakanGraph {
        private Map<String, Map<String, Integer>> graph = new HashMap<>();

        public void tambahRute(String tempatMakanAsal, String tempatMakanTujuan, int jarak) {
            graph.putIfAbsent(tempatMakanAsal, new HashMap<>());
            graph.get(tempatMakanAsal).put(tempatMakanTujuan, jarak);
        }

        public Map<String, Map<String, Integer>> getGraph() {
            return graph;
        }
    }

    public static List<String> cariRuteTerpendek(Map<String, Map<String, Integer>> graph, String tempatMakanAsal, String tempatMakanTujuan) {
        if (!graph.containsKey(tempatMakanAsal) || !graph.containsKey(tempatMakanTujuan)) {
            return null; // Tidak ada rute jika tempat makan asal atau tujuan tidak ditemukan
        }

        Map<String, Integer> jarakMin = new HashMap<>();
        Map<String, String> tempatMakanSebelumnya = new HashMap<>();
        Set<String> belumDikunjungi = new HashSet<>();

        for (String tempatMakan : graph.keySet()) {
            jarakMin.put(tempatMakan, Integer.MAX_VALUE);
            tempatMakanSebelumnya.put(tempatMakan, null);
            belumDikunjungi.add(tempatMakan);
        }

        jarakMin.put(tempatMakanAsal, 0);

        while (!belumDikunjungi.isEmpty()) {
            String tempatMakanSekarang = null;
            for (String tempatMakan : belumDikunjungi) {
                if (tempatMakanSekarang == null || jarakMin.get(tempatMakan) < jarakMin.get(tempatMakanSekarang)) {
                    tempatMakanSekarang = tempatMakan;
                }
            }

            if (tempatMakanSekarang.equals(tempatMakanTujuan)) {
                List<String> ruteTerpendek = new ArrayList<>();
                while (tempatMakanSekarang != null) {
                    ruteTerpendek.add(tempatMakanSekarang);
                    tempatMakanSekarang = tempatMakanSebelumnya.get(tempatMakanSekarang);
                }
                Collections.reverse(ruteTerpendek);
                return ruteTerpendek;
            }

            belumDikunjungi.remove(tempatMakanSekarang);

            for (String tetangga : graph.get(tempatMakanSekarang).keySet()) {
                int jarak = graph.get(tempatMakanSekarang).get(tetangga);
                int jarakBaru = jarakMin.get(tempatMakanSekarang) + jarak;
                if (jarakBaru < jarakMin.get(tetangga)) {
                    jarakMin.put(tetangga, jarakBaru);
                    tempatMakanSebelumnya.put(tetangga, tempatMakanSekarang);
                }
            }
        }

        return null; // Tidak ada rute yang ditemukan
    }

    public static void main(String[] args) {
        TempatMakanGraph tempatMakanGraph = new TempatMakanGraph();
        tempatMakanGraph.tambahRute("Warung Sate", "Pecel Lele", 5);
        tempatMakanGraph.tambahRute("Warung Sate", "Kedai Bakso", 8);
        tempatMakanGraph.tambahRute("Pecel Lele", "Kedai Bakso", 6);
        tempatMakanGraph.tambahRute("Warung Soto", "Warung Sate", 10);
        tempatMakanGraph.tambahRute("Warung Soto", "Kedai Bakso", 12);
        tempatMakanGraph.tambahRute("Warung Soto", "Pecel Lele", 15);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tempat Makan Asal: ");
        String tempatMakanAsal = scanner.nextLine();
        System.out.print("Tempat Makan Tujuan: ");
        String tempatMakanTujuan = scanner.nextLine();

        List<String> ruteTerpendek = cariRuteTerpendek(tempatMakanGraph.getGraph(), tempatMakanAsal, tempatMakanTujuan);

        if (ruteTerpendek != null) {
            System.out.println("Rute terpendek dari " + tempatMakanAsal + " ke " + tempatMakanTujuan + " adalah: " + ruteTerpendek);
        } else {
            System.out.println("Tidak ada rute yang tersedia dari " + tempatMakanAsal + " ke " + tempatMakanTujuan + ".");
        }
    }
}
