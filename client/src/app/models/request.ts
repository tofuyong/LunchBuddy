import { Time } from "@angular/common";

export class Request {
    constructor(
        public requestId: string,
        public preferredDate: Date,
        public preferredTime: Time,
        public preferredGender: string,
        public employeeId: string,
        public isMatched: boolean
    ) { };
}