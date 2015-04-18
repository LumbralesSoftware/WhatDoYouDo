package library.model;

/**
 * author: Artemiy Garin
 * Copyright (C) 2013 SQLite Simple Project
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressWarnings("CanBeFinal")
public class FTSModel {

    private String id; // or UUID (recommended)
    private int tableCategory;
    private String data;

    @SuppressWarnings("unused")
    public FTSModel(String id, int tableCategory, String data) {
        this.id = id;
        this.tableCategory = tableCategory;
        this.data = data;
    }

    @SuppressWarnings("unused")
    public FTSModel(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public int getTableCategory() {
        return tableCategory;
    }

}
