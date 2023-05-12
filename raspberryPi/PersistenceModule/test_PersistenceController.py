from unittest import TestCase

import PersistenceModule
from PersistenceModule.PersistenceController import PersistenceController


class TestPersistenceController(TestCase):
    def test_save_ride(self):
        per = PersistenceController()
        per.save_ride(None)
