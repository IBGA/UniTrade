import { loadFeature, defineFeature } from 'jest-cucumber';
import { beforeEach } from 'vitest';
import TestRenderer from 'react-test-renderer';
import { BrowseItemPostingPage } from '../../pages/BrowseItemPostingPage';
import { DELETE, GET, POST, LOGIN } from '../../utils/client';
import { ItemPostingThumbnail } from '../../components/ItemPosting.jsx';
import accessBackend from '../../utils/testUtils';

const feature = loadFeature(
  '../features/ID021_Browse_all_item_postings.feature'
);

let defaultUser = {
  email: 'default@user.com',
  username: 'default@user.com',
  firstName: 'Default',
  lastName: 'User',
  password: 'DefaultUser',
};

let testRenderer;
let testInstance;

// Mock navigate function,
vi.mock('react-router-dom', () => ({
  useNavigate: vi.fn(),
}));

defineFeature(feature, (test) => {
  beforeAll(async () => {
    // Create default user if it doesn't exist
    await POST('person', defaultUser, false);
  });

  beforeEach(async () => {
    testRenderer = TestRenderer.create(<BrowseItemPostingPage />);
    testInstance = testRenderer.root;
  });

  test('Browse all available item postings (Normal Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', async () => {
      await LOGIN(defaultUser.email, defaultUser.password);
    });

    and('there exists at least one item posting', async () => {
      await accessBackend(defaultUser, async () => {
        let items = await GET('itemPosting');
        if (items.length == 0) {
          let universityBody = {
            name: 'Test University',
            city: 'Test City',
            description: 'Test Description',
            moderationIds: [],
          };

          let universityRes = await POST('university', universityBody);

          let personBody = {
            email: 'test@mail.com',
            password: 'test',
            firstName: 'Test',
            lastName: 'Test',
            username: 'test',
            universityId: universityRes.id,
            lastOnline: '2023-02-27T05:35:25.955Z',
            profilePicture: 'string',
            enrolledCourseIds: [],
            online: true,
            enabled: true,
          };

          let personRes = await POST('person', personBody);

          let itemBody = {
            title: 'Test Item',
            description: 'Test Description',
            price: 10,
            universityId: universityRes.id,
            posterId: personRes.id,
            datePosted: '2023-02-27T05:35:25.955Z',
            courseIds: [],
          };

          let itemRes = await POST('itemPosting', itemBody);
        }
      });
    });

    when('user searches for all available item postings', () => {
      // empty cz UI sets the default to all postings
    });

    then('all available item postings are displayed', async () => {
      await accessBackend(defaultUser, async () => {
        let itemPostings = testInstance.findAllByType(ItemPostingThumbnail);
        let onDatabase = await GET('itemposting');
        expect(itemPostings.length).toBe(onDatabase.length);
      });
    });
  });

  test('No available item postings (Alternate Flow)', ({
    given,
    and,
    when,
    then,
  }) => {
    given('user is logged in', () => {
      // not implemented yet
    });

    and('there are no available item postings', async () => {
      await accessBackend(defaultUser, async () => {
        let itemPostingIds = (await GET('itemposting')).map((item) => item.id);
        itemPostingIds.forEach((id) => {
          DELETE(`itemposting/${id}`, true);
        });
      });
    });

    when('user searches for all available item postings', () => {
      // empty cz UI sets the default to all postings
    });

    then('no item postings are displayed', () => {
      // Check that the count of the items displayed is equal to 0
      let itemPostings = testInstance.findAllByType(ItemPostingThumbnail);
      expect(itemPostings.length).toBe(0);
    });
  });
});
