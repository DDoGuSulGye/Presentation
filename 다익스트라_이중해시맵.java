int dijkstra(String startNode, String endNode, HashMap<String, HashMap<String, Integer>> wholeGraph) {
        int n = wholeGraph.size();     //Node의 수
        int e = 0;                     //Edge의 수
        e = wholeGraph.keySet().stream().map((key) -> wholeGraph.get(key).size()).reduce(e, Integer::sum); //Edge의 수를 계산

        Map<String, Integer> keyToInteger = new HashMap<>(); // 노드들에 int index부여. 노드이름으로 index를 찾을수 있는 Map
        int keyIndex = 0;
        for (Object key : wholeGraph.keySet()) {
            keyToInteger.put(key.toString(), keyIndex);
            keyIndex++;
        }
        Map<Integer, String> integerToKey = new HashMap<>(); // index로 노드이름을 찾을수 있는 Map
        keyToInteger.keySet().forEach((key) -> {
            integerToKey.put((int) keyToInteger.get(key), key.toString());
        });

        int distance[] = new int[n];          //최단 거리를 저장할 변수
        boolean[] check = new boolean[n];     //해당 노드를 방문했는지 체크할 변수
        int maps[][] = new int[n][n];         //노드 간의 거리 배열
        wholeGraph.keySet().forEach((i) -> {  //노드 간의 거리 계산 및 저장
            wholeGraph.get(i).keySet().forEach((j) -> {
                maps[keyToInteger.get(i)][keyToInteger.get(j)] = wholeGraph.get(i).get(j);
            });
        });

        int v = keyToInteger.get(startNode);

        //distance값 초기화.
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //시작노드값 초기화.
        distance[v] = 0;
        check[v] = true;

        //연결노드 distance갱신
        for (int i = 0; i < n; i++) {
            if (!check[i] && maps[v][i] != 0) {
                distance[i] = maps[v][i];
            }
        }

        Map<Integer, Integer> temp = new LinkedHashMap<>();
        int tempp = 0;
		int minDistance = 0

        for (int a = 0; a < n - 1; a++) {
            //원래는 모든 노드가 true될때까지 인데
            //노드가 n개 있을 때 다익스트라를 위해서 반복수는 n-1번이면 된다.
            //원하지 않으면 각각의 노드가 모두 true인지 확인하는 식으로 구현해도 된다.
            int min = Integer.MAX_VALUE;
            int min_index = -1;

            //최소값 찾기
            for (int i = 0; i < n; i++) { //기준 노드에서 인접 노드중 가장 가까운 노드 선택
                if (!check[i] && distance[i] != Integer.MAX_VALUE) { //체크안되있고 거리가 무한이 아닌 노드
                    if (distance[i] < min) {
                        min = distance[i];
                        min_index = i;
                        tempp = i;
                    }
                }
            }
            check[min_index] = true;
            for (int i = 0; i < n; i++) {
                if (!check[i] && maps[min_index][i] != 0) {
                    if (distance[i] > distance[min_index] + maps[min_index][i]) {
                        distance[i] = distance[min_index] + maps[min_index][i];
                    }
                    if (i == keyToInteger.get(endNode)) {
                        temp.put(tempp, distance[i]);
						minDistance = distance[i];
                    }
                }
            }
        }
        return minDistance;
    }