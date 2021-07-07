import { InMemoryDbService } from "angular-in-memory-web-api";
import { Injectable } from "@angular/core";
import { Task } from "./task.model";
@Injectable({
    providedIn: 'root'
  })
export class FakeBackendService implements InMemoryDbService {
    createDb() {
        let tasks:Task[] = [
            {
                id: 1,
                description: "Start code"
            },
            {
                id: 2,
                description: "install npm"
            }
        ];
        return {
            tasks: tasks
        };
    }


}