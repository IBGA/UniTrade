import { loadFeatures, autoBindSteps } from 'jest-cucumber';

// Just import all the step definitions here
import { exampleSteps } from './step_definitions/example.steps.js';

const features = loadFeatures('src/../../features/*.feature');

// Add the imported step definitions into the second argument array
autoBindSteps(features, [exampleSteps]);
